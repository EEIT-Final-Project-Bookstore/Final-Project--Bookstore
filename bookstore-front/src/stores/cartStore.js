import { nextTick } from "vue";
import { defineStore } from "pinia";
import { useAuthStore } from "@/stores/authStore";
import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

export const useCartStore = defineStore("cart", {
    state: () => ({
        cartItems: [],
        selectedItems: [], // 這是被選取的項目
        totalItems: 0,
        cartItemsCount: 0,
        totalPrice: 0,
        discountAmount: parseFloat(localStorage.getItem("discountAmount")) || 0, // 從 localStorage 讀取折扣金額
        couponCode: localStorage.getItem("couponCode") || '',  // 從 localStorage 讀取優惠券代碼
        finalPrice: 0,
    }),
    actions: {
        async fetchCartItems() {
            const authStore = useAuthStore();
            if (!authStore.isAuthenticated || !authStore.customerId) return;
            try {
                const response = await axiosapi.get(`/api/cart/view/${authStore.customerId}`);
                console.log(response.data);
                this.cartItems = [];  // 先清空購物車
                await nextTick();  // 等 DOM 重新渲染
                this.cartItems = response.data;  // 再賦值，確保響應式更新
                console.log("更新後的 cartItems:", this.cartItems);
                // 更新選中項目
                this.updateSelectedItems();
                this.calculateTotals();
            } catch (error) {
                console.error("獲取購物車失敗", error);
            }
        },
        async updateQuantity(item, change) {
            const authStore = useAuthStore();
            const newQuantity = item.quantity + change;
            if (newQuantity < 1) return;
            try {
                await axiosapi.put(`/api/cart/update`, {
                    customerId: authStore.customerId,
                    bookId: item.bookId,
                    quantity: newQuantity,
                });
                item.quantity = newQuantity;
                // 更新商品的小計
                item.subtotal = item.quantity * item.price;
                this.calculateTotals();
            } catch (error) {
                console.error("更新數量失敗", error);
            }
        },
        selectAll() {
            if (this.selectedItems.length === this.cartItems.length) {
                // 如果已經全選，則取消選擇所有商品
                this.cartItems.forEach(item => item.selected = false);
                this.selectedItems = [];
            } else {
                // 否則選擇所有商品
                this.cartItems.forEach(item => {
                    item.selected = true;
                    if (!this.selectedItems.includes(item)) {
                        this.selectedItems.push(item);
                    }
                });
            }
        },
        updateSelectedItems() {
            // 更新被選取的商品
            this.selectedItems = this.cartItems.filter(item => item.selected);
        },
        async removeSelectedItems() {
            try {
                const authStore = useAuthStore();
                const selectedItems = this.cartItems.filter(item => item.selected);
                if (selectedItems.length === 0) return; // 沒有選取則不執行
                // 發送刪除請求給後端
                for (const item of selectedItems) {
                    await axiosapi.delete('/api/cart/delete', {
                        data: {
                            customerId: authStore.customerId, // 確保這裡有正確的 customerId
                            bookId: item.bookId
                        }
                    });
                }
                // this.cartItems = this.cartItems.filter(item => !item.selected);
                this.calculateTotals();
                // 重新獲取購物車，確保畫面更新
                await this.fetchCartItems();
                Swal.fire("成功", "已刪除選取的商品", "success"); // 顯示成功提示
            } catch (error) {
                console.error('刪除選取的項目失敗', error);
                Swal.fire("錯誤", "刪除商品失敗", "error"); // 顯示錯誤提示
            }
        },
        async removeItem(index) {
            try {
                console.log("收到的 index:", index);
                console.log("目前 cartItems:", this.cartItems);

                if (index === undefined || index < 0 || index >= this.cartItems.length) {
                    console.error("無效的索引值:", index);
                    return;
                }

                const item = this.cartItems[index];
                console.log("刪除前的商品:", item);

                if (!item) {
                    console.error("商品不存在");
                    return;
                }

                const authStore = useAuthStore();
                // 發送刪除請求
                await axiosapi.delete('/api/cart/delete', {
                    data: {
                        customerId: authStore.customerId,
                        bookId: item.bookId
                    }
                });
                // 刪除該商品
                this.cartItems.splice(index, 1);
                console.log("刪除後的商品列表:", this.cartItems);
                // 計算總數與價格
                this.calculateTotals();
            } catch (error) {
                console.error("刪除商品失敗", error);
            }
        },
        async clearCart() {
            try {
                const authStore = useAuthStore();
                // 清空整個購物車
                await axiosapi.delete(`/api/cart/clear/${authStore.customerId}`);
                // 清除本地購物車資料
                this.cartItems = [];
                this.selectedItems = [];
                // 清除 localStorage 中的優惠券資料
                localStorage.removeItem("couponCode");
                localStorage.removeItem("discountAmount");
            } catch (error) {
                console.error('清空購物車時發生錯誤:', error);
            }
        },
        async applyCoupon() {
            const authStore = useAuthStore();
            try {
                const response = await axiosapi.post(`/api/coupon/apply`, {
                    cartId: authStore.cartId,
                    couponCode: this.couponCode,
                });
                if (response.data.valid) {
                    this.discountAmount = response.data.discountAmount;
                    // 儲存優惠券和折扣金額到 localStorage
                    localStorage.setItem("couponCode", this.couponCode);
                    localStorage.setItem("discountAmount", this.discountAmount);
                    this.calculateTotals();
                    Swal.fire("成功", "優惠券已套用", "success");
                } else {
                    Swal.fire("錯誤", response.data.message, "error");
                }
            } catch (error) {
                console.error("套用優惠券失敗", error);
                Swal.fire("錯誤", "無法套用優惠券", "error");
            }
        },
        async removeCoupon() {
            const authStore = useAuthStore();
            try {
                const response = await axiosapi.delete(`/api/coupon/remove`, {
                    data: {
                        cartId: authStore.cartId,
                        couponCode: this.couponCode
                    }
                });
                if (response.data.valid) {
                    this.discountAmount = 0;
                    this.couponCode = "";  // 清空優惠券代碼
                    // 清除 localStorage 中的優惠券資料
                    localStorage.removeItem("couponCode");
                    localStorage.removeItem("discountAmount");
                    this.calculateTotals();
                    Swal.fire("成功", "優惠券已移除", "success");
                } else {
                    Swal.fire("錯誤", response.data.message, "error");
                }
            } catch (error) {
                console.error("移除優惠券失敗", error);
                Swal.fire("錯誤", "無法移除優惠券", "error");
            }
        },
        // 更新最終價格
        updatefinalAmount(price) {
            this.finalAmount = price;  // 更新 finalPrice
        },
        calculateTotals() {
            this.totalItems = this.cartItems.reduce((sum, item) => sum + item.quantity, 0);
            this.cartItemsCount = this.cartItems.length;
            this.totalPrice = this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
            this.finalPrice = Math.max(this.totalPrice - this.discountAmount, 0);
        },
    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: "cart",
                storage: localStorage, // 儲存至 localStorage
                paths: ["couponCode", "discountAmount"],  // 只儲存優惠券和折扣金額
            },
        ],
    },
});


// 用來選擇/取消選擇單一商品
// toggleSelect(bookId) {
//     const item = this.cartItems.find(item => item.bookId === bookId);
//     if (item) {
//         item.selected = !item.selected; // 切換選擇狀態
//         if (item.selected) {
//             // 如果選中，將其加入 selectedItems
//             this.selectedItems.push(item);
//         } else {
//             // 如果取消選擇，從 selectedItems 移除
//             this.selectedItems = this.selectedItems.filter(i => i.bookId !== bookId);
//         }
//     }
// },

// async removeItem(bookId) {
//     try {
//         await axiosapi.delete(`/api/cart/delete`);
//         this.cartItems = this.cartItems.filter(item => item.bookId !== bookId);
//         this.calculateTotals();
//     } catch (error) {
//         console.error("移除商品失敗", error);
//     }
// },

// 用來檢查某個商品是否已經被選中
// isSelected(itemId) {
//     return this.selectedItems.includes(itemId);
// },