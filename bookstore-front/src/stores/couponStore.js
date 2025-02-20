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
        },
        categorizeExpiredCoupons() {
            // 根據過期日期將優惠券分為有效、已使用和已過期
            const currentDate = new Date();
            this.coupons.valid = this.coupons.valid.filter(coupon => new Date(coupon.endDate) > currentDate);
            this.coupons.expired = this.coupons.valid.filter(coupon => new Date(coupon.endDate) < currentDate);
        }
    },
});
