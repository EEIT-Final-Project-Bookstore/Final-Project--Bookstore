import { defineStore } from "pinia";

export const useCartStore = defineStore("cart", {
    state: () => ({
        customerId: null, // 儲存顧客 ID
        customerName: "", // 可額外儲存顧客名稱
        cartItems: [], // 存放購物車項目
        // discountAmount: 0, // 優惠金額 from gpt 不確定需不需要
    }),
    actions: {
        // 登入時儲存顧客資料 設定顧客 ID 和名稱
        setCustomer(customerId, customerName) {
            this.customerId = customerId;
            this.customerName = customerName;
        },
        setCartItems(items) {
            this.cartItems = items;
        },
        addItemToCart(item) {
            this.cartItems.push(item);
        },
        updateQuantity(item, delta) {
            const existingItem = this.cartItems.find(i => i.bookId === item.bookId);
            if (existingItem) {
                existingItem.quantity += delta;
                if (existingItem.quantity < 1) existingItem.quantity = 1;
            }
        },
        removeItem(bookId) {
            this.cartItems = this.cartItems.filter(item => item.bookId !== bookId);
        },
        selectItems(item) {
            if (item.selected) {
                this.selectedItems.push(item);
            } else {
                this.selectedItems = this.selectedItems.filter(i => i.bookId !== item.bookId);
            }
        },
    },
}, {
    persist: {  //pinia-plugin-persistedstate的屬性，讓網頁重整後登入資料還在
        storage: sessionStorage,
    }
})

export default useCartStore
