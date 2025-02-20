import { defineStore } from "pinia";
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios";

export const useOrderStore = defineStore("order", {
    state: () => ({
        orders: [], // 訂單列表
        latestOrder: null,
        orderDetail: null,
    }),
    actions: {
        async fetchLatestOrder(customerId) {
            try {
                const response = await axiosapi.get(`/ecpay/paymentResult/${customerId}`);
                this.latestOrder = response.data;
            } catch (error) {
                console.error("取得訂單失敗:", error);
            }
        },
        async fetchOrders(customerId) {
            try {
                const response = await axiosapi.post(`/api/order/list/${customerId}`, {
                    page: 0,  // 可以改成你想要的頁數
                    size: 10, // 可以改成你想要的每頁大小
                });
                this.orders = response.data;
            } catch (error) {
                console.error("獲取訂單資訊錯誤:", error);
            }
        },
        async fetchOrderDetail(orderId) {
            try {
                const response = await axiosapi.get(`/api/order/detail/${orderId}`);
                this.orderDetail = response.data;
            } catch (error) {
                console.error("獲取訂單明細錯誤:", error);
            }
        },
        // async cancelOrder(orderId) {
        //     try {
        //         const { data } = await axiosapi.post(`/api/order/cancel/${orderId}`);
        //         Swal.fire("成功", data.message, "success");
        //         // 取消後，更新 Pinia 的 `orders` 狀態
        //         this.orders = this.orders.map(order =>
        //             order.orderId === orderId ? { ...order, detailedStatus: "已取消" } : order
        //         );
        //     } catch (error) {
        //         Swal.fire("錯誤", "取消訂單失敗", "error");
        //     }
        // },
    },
});
