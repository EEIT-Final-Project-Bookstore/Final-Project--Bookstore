<template>
    <el-dialog v-model="visible" title="修改優惠券" width="500px">
        <el-form :model="form" label-width="150px">
            <el-form-item label="優惠券折扣碼">
                <el-input v-model="form.couponCode" style="width: 300px" />
            </el-form-item>
            <el-form-item label="折扣金額">
                <el-input v-model="form.discount" :min="0" style="width: 300px" />
            </el-form-item>
            <el-form-item label="開始日期">
                <el-date-picker v-model="form.startDate" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 300px" />
            </el-form-item>
            <el-form-item label="結束日期">
                <el-date-picker v-model="form.endDate" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 300px" />
            </el-form-item>
            <el-form-item label="最低消費金額">
                <el-input v-model="form.minimumAmount" :min="0" style="width: 300px" />
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="updateCoupon">更新</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, defineExpose, defineEmits } from 'vue';
import dayjs from 'dayjs';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios.js';

const visible = ref(false);
const form = ref({});
const emit = defineEmits(['coupon-updated']);

const open = (coupon) => {
    console.log("開啟修改對話框，傳入的優惠券資料：", coupon);
    form.value = { ...coupon };
    visible.value = true;
};

const updateCoupon = async () => {
    try {
        // 確保日期是 dayjs 物件並格式化
        const formattedStartDate = dayjs(form.value.startDate).format('YYYY/MM/DD HH:mm:ss');
        const formattedEndDate = dayjs(form.value.endDate).format('YYYY/MM/DD HH:mm:ss');
        const couponToUpdate = {
            couponCode: form.value.couponCode,
            discount: form.value.discount,
            startDate: formattedStartDate,
            endDate: formattedEndDate,
            minimumAmount: form.value.minimumAmount
        };
        const response = await axiosapi.put(`/api/coupon/admin/update/${form.value.couponId}`, couponToUpdate);
        console.log('優惠券更新成功:', response.data);
        Swal.fire({
            icon: 'success',
            title: '更新成功',
            text: response.data.message
        });
        // 關閉 Dialog
        visible.value = false;
        // 通知父組件重新拉取優惠券列表
        emit('coupon-updated');
    } catch (error) {
        console.error('更新優惠券失敗:', error);
        Swal.fire({
            icon: 'error',
            title: '更新失敗',
            text: '更新優惠券時發生錯誤，請稍後再試。'
        });
    }
};

defineExpose({ open });
</script>

<style scoped>
.el-form-item__label {
    margin-left: auto;
}
</style>