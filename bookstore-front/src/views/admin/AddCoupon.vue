<template>
    <div class="coupon-form-container">
        <el-card class="coupon-card">
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
                    style="width: 300px" />
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
                    <el-button type="primary" @click="submitCoupon">新增優惠券</el-button>
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
        align-items: center;
        height: 450px; /* 讓區塊置中 */
        background-color: #f4f4f4;
        /* padding: 20px; */
    }
    /* 卡片樣式 */
    .coupon-card {
        width: 500px;
        height: 350px;
        padding: 10px;
        background: #fff;
        border-radius: 15px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    }
    .el-card__body {
        padding: 15px;
        margin-top: 8px;
    }
    /* 深色標籤 */
    .el-form-item__label {
        font-size: 18px;
        font-weight: 700;
    }
    /* 調整輸入框大小 */
    .input-large {
        width: 100%;
        font-size: 16px;
    }
    /* 按鈕美化 */
    .submit-button {
        width: 100%;
        font-size: 17px;
        padding: 10px 0;
    }
</style>