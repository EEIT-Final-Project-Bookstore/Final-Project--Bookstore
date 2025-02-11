import { defineStore } from 'pinia';
import axiosapi from '@/plugins/axios.js';

export const useCouponStore = defineStore('couponStore', {
    state: () => ({
        coupons: {
            expired: [],
            valid: [],
            used: []
        },
    }),
    actions: {
        async fetchCustomerCoupons(customerId) {
            try {
                const { data } = await axiosapi.get(`/api/coupon/customer/${customerId}`);
                this.coupons = data;
            } catch (error) {
                console.error("查詢優惠券失敗:", error);
            }
        }
    }
});
