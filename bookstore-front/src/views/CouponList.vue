<template>
    <div class="coupon-container">
        <h2>我的優惠券</h2>
        <el-tabs v-model="activeTab" class="coupon-el-tabs">
            <!-- 可使用的優惠券 -->
            <el-tab-pane label="未使用" name="valid" class="el-tab-labels">
                <div v-if="coupons.valid.length">
                    <div class="coupon-list">
                        <div v-for="coupon in coupons.valid" :key="coupon.message" class="coupon-card">
                        <div class="coupon-header">
                            <span class="coupon-code">{{ coupon.message.split(' ')[1] }}</span>
                        </div>
                        <div class="coupon-body">
                            <p class="discount-amount">${{ coupon.discountAmount }} OFF</p>
                            <p class="min-amount">最低消費：${{ coupon.minimumAmount }}</p>
                            <p class="expiry-date">有效期限：{{ coupon.message.split('until')[1] }}</p>
                        </div>
                        </div>
                    </div>
                </div>
                <p v-else class="no-coupons-block">目前沒有可用的優惠券。</p>
            </el-tab-pane>
            <!-- 已使用的優惠券 -->
            <el-tab-pane label="已使用" name="used">
                <div v-if="coupons.used.length">
                    <div class="coupon-list">
                        <div v-for="coupon in coupons.used" :key="coupon.message" class="coupon-card">
                            <div class="coupon-header">
                                <span class="coupon-code">{{ coupon.message.split(' ')[1] }}</span>
                            </div>
                            <div class="coupon-body">
                                <p class="discount-amount">${{ coupon.discountAmount }} OFF</p>
                                <p class="min-amount">最低消費：${{ coupon.minimumAmount }}</p>
                                <p class="expiry-date">有效期限：{{ coupon.message.split('until')[1] }}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <p v-else class="no-coupons-block">您尚未使用任何優惠券。</p>
            </el-tab-pane>
            <!-- 已過期的優惠券 -->
            <el-tab-pane label="已過期" name="expired">
                <div v-if="coupons.expired.length">
                    <div class="coupon-list">
                        <div v-for="coupon in coupons.expired" :key="coupon.message" class="coupon-card">
                            <div class="coupon-header">
                                <span class="coupon-code">{{ coupon.message.split(' ')[1] }}</span>
                            </div>
                            <div class="coupon-body">
                                <p class="discount-amount">${{ coupon.discountAmount }} OFF</p>
                                <p class="min-amount">最低消費：${{ coupon.minimumAmount }}</p>
                                <p class="expiry-date">有效期限：{{ coupon.message.split('until')[1] }}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <p v-else class="no-coupons-block">目前沒有已過期的優惠券。</p>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from "vue-router";
import { useAuthStore } from '@/stores/authStore';
import { useCouponStore } from '@/stores/couponStore';

const router = useRouter();
const authStore = useAuthStore();
const couponStore = useCouponStore();

onMounted(async() => {
    if (!authStore.customerId) {
      router.push('/login');
      return;
    }
    couponStore.fetchCustomerCoupons(authStore.customerId);
    console.log("優惠券資料：", couponStore.coupons);
});

const coupons = computed(() => couponStore.coupons);

// 預設選中的 tab 名稱
const activeTab = ref("valid");  // 預設顯示 "可使用" 這個 tab
</script>

<style>
.coupon-container {
  text-align: center;
}
h2 {
    text-align: center;
    color: #333;
    display: block;
    font-size: 28px;
    margin-bottom: 0;
    margin-block-start: 0.67em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    font-weight: bold;
}
.el-tabs {
    margin-left: 100px;
    margin-right: 100px;
}
/* 調整 tab 項目的字體大小 */
.el-tabs__item{
  font-size: 18px !important;
  font-weight: bold !important;
}
.el-tabs__item.is-active {  /* 當 tab 選中時的樣式 */
  font-size: 20px !important; 
  color: #259de2 !important;
}
.coupon-list {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: center;
}
.coupon-card {
  width: 250px;
  background: #fffae6;
  border: 2px dashed #ff9800;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}
.coupon-header {
  font-size: 1.2em;
  font-weight: bold;
  color: #ff5722;
}
.coupon-body {
  margin-top: 10px;
}
.discount-amount {
  font-size: 1.5em;
  font-weight: bold;
  color: #e91e63;
}
.min-amount {
  font-size: 1em;
  color: #4caf50;
  font-weight: bold;
  margin-top: 5px;
}
.expiry-date {
  font-size: 0.9em;
  color: #757575;
  margin-top: 5px;
}
.no-coupons-block {
    width: 1000px;
    border: 2px solid rgb(56, 163, 250);
    border-radius: 5px;
    text-align: center;
    display: flex;
    justify-content: center;
    margin: 0 auto;
    padding: 30px;
    background-color: #e5f3fe;
    color: #333;
    font-size: 1.1rem;
    font-weight: bold;
}
</style>
