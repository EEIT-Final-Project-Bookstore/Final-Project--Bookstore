<template>
    <div class="coupon-form-container">
        <el-card class="add-coupon-card">
            <el-form :model="coupon" label-width="auto" @submit.prevent="submitCoupon">
                <el-form-item label="優惠券折扣碼">
                    <el-input
                        v-model="coupon.couponCode"
                        placeholder="請輸入優惠券折扣碼"
                        style="width: 300px" required
                        class="input-large"
                    />
                </el-form-item>
                <el-form-item label="折扣金額">
                    <el-input 
                        v-model="coupon.discount" 
                        placeholder="請輸入折扣金額" required
                        class="input-large"
                        style="width: 300px"
                    />
                </el-form-item>
                <el-form-item label="開始日期">
                    <el-date-picker
                        v-model="coupon.startDate"
                        type="datetime"
                        placeholder="選擇開始日期及時間" required  
                        class="input-large"
                        style="width: 300px" 
                        :disabled-date="disabledStartDate"
                    />
                </el-form-item>
                <el-form-item label="結束日期">
                    <el-date-picker
                    v-model="coupon.endDate" 
                    type="datetime" 
                    placeholder="選擇結束日期及時間" required  
                    class="input-large" 
                    style="width: 300px"
                />
                </el-form-item>
                <el-form-item label="最低消費金額">
                    <el-input 
                        v-model="coupon.minimumAmount" 
                        placeholder="請輸入最低消費金額" required  
                        class="input-large" 
                        style="width: 300px"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitCoupon" class="add-coupon-submit-button">新增優惠券</el-button>
                </el-form-item>
            </el-form>
    </el-card>
</div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import Swal from 'sweetalert2';
import dayjs from 'dayjs';
import axiosapi from '@/plugins/axios.js';

const emit = defineEmits(['couponAdded']);

const coupon = ref({
    couponCode: '',
    discount: null,
    startDate: '',
    endDate: '',
    minimumAmount: null
});

const disabledStartDate = (date) => {
    const now = new Date();
    return date.getTime() < now.getTime(); // 禁止選擇當前時間之前
};

const submitCoupon = async () => {
    if (!coupon.value.couponCode || !coupon.value.discount || !coupon.value.startDate || !coupon.value.endDate || !coupon.value.minimumAmount) {
        Swal.fire({
            icon: 'warning',
            title: '請填寫完整優惠券資訊',
            confirmButtonText: '確定'
        });
        return;
    }
    // 轉換日期格式
    const formattedStartDate = dayjs(coupon.value.startDate).format('YYYY/MM/DD HH:mm:ss');
    const formattedEndDate = dayjs(coupon.value.endDate).format('YYYY/MM/DD HH:mm:ss');
    const payload = {
        ...coupon.value,
        startDate: formattedStartDate,
        endDate: formattedEndDate
    };
    console.log("發送資料:", payload);
    try {
        const response = await axiosapi.post('/api/coupon/admin/addAndAssign', payload);
        Swal.fire({
            icon: 'success',
            title: '優惠券新增成功！',
            text: response.data,
            confirmButtonText: '確定'
        });
        console.log(response.data);
        coupon.value = { couponCode: '', discount: null, startDate: '', endDate: '', minimumAmount: null };  // 清空表單
        emit('couponAdded'); // 🔥 通知父元件優惠券已新增，請求最新優惠券資料
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: '新增優惠券失敗',
            text: '新增優惠券時發生錯誤'
        });
        console.error('新增失敗:', error);
    }
};
</script>

<style>
/* 主要容器，讓表單不要貼邊 */
.coupon-form-container {
    display: flex;
    justify-content: center;
    align-items: center;  /* 讓區塊置中 */
    height: 450px;
    background-color: #f4f4f4;
}
/* 卡片樣式 */
.add-coupon-card {
    width: 500px;
    height: 380px;
    padding: 15px;
    background: #fff;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}
.el-card__body {
    padding: 15px !important;
    margin-top: 8px !important;
}
/* 深色標籤 */
.el-form-item__label {
    font-size: 18px !important;
    font-weight: 700 !important;
}
/* 讓日期選擇器彈出的面板也變大 */

/* 調整星期欄位和日期選擇器內部元素的間距 */
/* .el-date-picker__header {
    font-size: 18px !important;
} */
/* 調整輸入框大小 */
.input-large {
    width: 100% !important;
    font-size: 15px !important;
}
/* 按鈕美化 */
.add-coupon-submit-button {
    width: 200px !important;
    font-size: 18px !important;
    padding: 10px 0 !important;
    margin: auto;
}
</style>