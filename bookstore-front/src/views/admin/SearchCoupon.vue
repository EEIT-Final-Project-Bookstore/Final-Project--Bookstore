<template>
    <div class="coupon-container">
        <el-table :data="coupons" class="custom-table" stripe>
            <el-table-column label="優惠券折扣碼" prop="couponCode" align="center"></el-table-column>
            <el-table-column label="折扣金額" prop="discount" align="center"></el-table-column>
            <el-table-column label="開始日期" prop="startDate" align="center"></el-table-column>
            <el-table-column label="結束日期" prop="endDate" align="center"></el-table-column>
            <el-table-column label="最低消費金額" prop="minimumAmount" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
            <template #default="{ row }">
                <el-button size="small" type="primary" @click="editDialog.open(row)">修改</el-button>
                <el-button size="small" type="danger" @click="deleteCoupon(row.couponId)">移除</el-button>
            </template>
            </el-table-column>
        </el-table>
    </div>
    <EditCouponDialog ref="editDialog" @coupon-updated="fetchCoupons" />
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElTable, ElTableColumn, ElButton } from 'element-plus';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios.js';
import EditCouponDialog from './EditCouponDialog.vue';

const coupons = ref([]);
const editDialog = ref(null);

const fetchCoupons = async () => {
try {
    const response = await axiosapi.get('/api/coupon/admin/coupons');
    coupons.value = response.data;
    console.log('查詢優惠券:', response.data);
} catch (error) {
    console.error('查詢優惠券失敗:', error);
}
};

const deleteCoupon = async (couponId) => {
    const result = await Swal.fire({
        title: "確定要刪除嗎？",
        text: "此操作無法還原！",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "刪除",
        cancelButtonText: "取消",
    });
    if (result.isConfirmed) {
        try {
            const response = await axiosapi.delete(`/api/coupon/admin/delete/${couponId}`);
            console.log('刪除優惠券:', response.data);
            if (!response.data.valid) {
                // 如果優惠券已被訂單使用，顯示"該優惠券已被訂單使用，無法刪除"
                Swal.fire({
                    icon: "error",
                    title: "無法刪除",
                    text: response.data.message,
                });
                return;
            }
            await Swal.fire({  // 如果成功刪除，顯示成功訊息
                icon: "success",
                title: "刪除成功",
                text: response.data.message,
            });
            fetchCoupons();  // 更新優惠券列表
        } catch (error) {
            console.error('刪除優惠券失敗:', error);
            Swal.fire({
                icon: "error",
                title: "刪除失敗",
                text: "發生錯誤，請稍後再試。",
            });
        };
    }
};

onMounted(fetchCoupons);

// 暴露給外部元件使用
defineExpose({ fetchCoupons })
</script>

<style scoped>
    .custom-table {
        width: 100%;
        --el-table-border-color: #cbc7c7;
    }
    .el-table :deep(.el-table__header) {
        margin:0;
    }
    /* 讓表格標題變大 */
    .el-table :deep(.el-table__header th) {
        font-size: 18px; /* 表頭字體加大 */
        text-align: center;
        color: rgb(52, 51, 51);
        border-color: rgb(84, 83, 83);
    }
    /* 調整表格標題與第一筆資料的間距 */
    .el-table :deep(.el-table__body) {
        margin-top: -2px; /* 減少標題與第一筆資料的間距 */
    }
    /* 讓所有欄位的內容置中 */
    .el-table :deep(.el-table__cell) {
        text-align: center;
        font-size: 16px;
        color: rgb(91, 90, 90);
    }
    /* 按鈕間距 */
    .el-button {
        margin: 0 4px;
        font-size: 15px;
        width: 53px;
        height: 40px;
    }
    /* 表格 hover 時改變背景顏色 */
    .el-table :deep(.el-table__row):hover {
        background-color: #e5e7ea;
    }
</style>
