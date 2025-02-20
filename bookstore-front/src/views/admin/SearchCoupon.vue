<template>
  <div class="coupon-container">
    <table class="custom-table">
      <thead>
        <tr>
          <th>優惠券折扣碼</th>
          <th>折扣金額</th>
          <th>開始日期</th>
          <th>結束日期</th>
          <th>最低消費金額</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="coupon in coupons" :key="coupon.couponId">
          <td>{{ coupon.couponCode }}</td>
          <td>{{ coupon.discount }}</td>
          <td>{{ coupon.startDate }}</td>
          <td>{{ coupon.endDate }}</td>
          <td>{{ coupon.minimumAmount }}</td>
          <td>
            <el-icon @click="editDialog.open(coupon)"><Edit /></el-icon>
            <el-icon @click="deleteCoupon(coupon.couponId)"><Delete /></el-icon>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <div class="pagination-container">
    <button
      :disabled="currentPage === 1"
      @click="changePage(currentPage - 1)"
      class="page-item prev-next"
    >
      上一頁
    </button>
    <button
      v-for="page in totalPagesArray"
      :key="page"
      :class="['page-item', { active: currentPage === page }]"
      @click="changePage(page)"
    >
      {{ page }}
    </button>
    <button
      :disabled="currentPage === totalPages"
      @click="changePage(currentPage + 1)"
      class="page-item prev-next"
    >
      下一頁
    </button>
  </div>

  <EditCouponDialog ref="editDialog" @coupon-updated="fetchCoupons" />
</template>

<style scoped>
.coupon-container {
  width: 80%;
  margin: auto;
  margin-top: 10px;
}
.custom-table {
  width: 90%;
  border-collapse: collapse;
  border: 1px solid #d9d9d9;
  background-color: #fff;
}
.custom-table th,
.custom-table td {
  border: 1px solid #ccc;
  padding: 10px;
  font-size: 18px;
  text-align: center;
}
.custom-table th {
  background-color: #f4f4f4;
  color: #333;
}
.custom-table tbody tr:hover {
  background-color: #f1f1f1;
}
.custom-table td
.action-buttons {
  display: flex;
  justify-content: center;
  gap: 8px;
}
.icon {
  cursor: pointer;
  font-size: 18px;
  padding: 5px;
  border-radius: 5px;
}
.icon.edit {
  color: #007bff;
}
.icon.delete {
  color: #d33;
}
.pagination-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 6px;
  margin-top: 15px;
  margin-bottom: 15px;
}
.page-item {
  width: 80px;
  padding: 5px 10px;
  border: 1px solid #ddd;
  background-color: #fff;
  color: #333;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}
.page-item:hover {
  background-color: #1e88f8;
  color: white;
}
.page-item.active {
  background-color: #0056b3;
  color: #fff;
}
.prev-next {
  padding: 5px 15px;
  background-color: #f8f9fa;
  border-radius: 5px;
}
.prev-next:disabled {
  background-color: #e7e4e4;
  color: black;
  cursor: not-allowed;
  opacity: 0.5;
}
</style>

<script setup>
import { ref, computed, onMounted } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue"; // Element-plus 的 Icons
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios.js";
import EditCouponDialog from "./EditCouponDialog.vue";

const coupons = ref([]);
const editDialog = ref(null);
// 分頁:
const currentPage = ref(1);
const pageSize = ref(5); // 每頁顯示 5 筆
const totalPages = ref(1);

const fetchCoupons = async () => {
  try {
    const response = await axiosapi.post("/api/coupon/admin/coupons", {
      page: currentPage.value - 1, //加上分頁功能
      size: pageSize.value,
    });
    coupons.value = response.data.content;
    console.log("查詢優惠券:", response.data);
    totalPages.value = response.data.totalPages;
  } catch (error) {
    console.error("查詢優惠券失敗:", error);
  }
};

// 分頁變更時觸發
const changePage = (pageNumber) => {
  currentPage.value = pageNumber;
  fetchCoupons();
};

// 獲取分頁頁碼陣列
const totalPagesArray = computed(() => {
  const pages = [];
  for (let i = 1; i <= totalPages.value; i++) {
    pages.push(i);
  }
  return pages;
});

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
      console.log("刪除優惠券:", response.data);
      if (!response.data.valid) {
        // 如果優惠券已被訂單使用，顯示"該優惠券已被訂單使用，無法刪除"
        Swal.fire({
          icon: "error",
          title: "無法刪除",
          text: response.data.message,
        });
        return;
      }
      await Swal.fire({
        // 如果成功刪除，顯示成功訊息
        icon: "success",
        title: "刪除成功",
        text: response.data.message,
      });
      fetchCoupons(); // 更新優惠券列表
    } catch (error) {
      console.error("刪除優惠券失敗:", error);
      Swal.fire({
        icon: "error",
        title: "刪除失敗",
        text: "發生錯誤，請稍後再試。",
      });
    }
  }
};

onMounted(fetchCoupons);

// 暴露給外部元件使用
defineExpose({ fetchCoupons });
</script>

<style scoped>
.coupon-container {
  width: 90%;
  margin-top: 10px;
  margin: auto !important;
}
.custom-table {
  --el-table-border-color: #cbc7c7;
  border: 1px solid #d9d9d9;
  margin: auto !important;
}
.custom-table td {
    border: 0;
    border-top: 1px solid #d9d9d9;
}
.el-table :deep(.el-table__header) {
  margin: 0;
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
  margin-top: -1px; /* 減少標題與第一筆資料的間距 */
}
/* 讓所有欄位的內容置中 */
.el-table :deep(.el-table__cell) {
  text-align: center;
  font-size: 18px;
  color: rgb(91, 90, 90);
}
/* 按鈕間距 */
.el-icon {
  font-size: 22px;
  width: 45px;
  height: 40px;
  padding: 0;
  cursor: pointer;
  border: 1px solid rgb(230, 228, 228);
  border-radius: 5px;
}
/* 表格 hover 時改變背景顏色 */
.el-table :deep(.el-table__row):hover {
  background-color: #e0e1e4 !important;
}
/* 分頁: */
.pagination-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 6px; /* 調整按鈕和頁碼之間的間距 */
  margin-top: 15px;
  margin-bottom: 15px;
}
.page-item {
  width: 80px;
  margin: 0 5px;
  padding: 5px 10px;
  border: 1px solid #ddd;
  background-color: #fff;
  color: #333;
  border-radius: 5px;
  cursor: pointer;
}
.page-item:hover {
  background-color: #1e88f8;
  color: white;
}
.page-item.active {
  background-color: #0056b3;
  color: #fff;
  border-color: #0056b3;
}
.page-item.disabled {
  background-color: #f8f9fa;
  border-color: #ddd;
  color: #ccc;
}
.prev-next {
  padding: 5px 15px;
  background-color: #f8f9fa;
  border-radius: 5px;
}
.prev-next.page-item:disabled {
  background-color: #e7e4e4;
  color: black;
  cursor: not-allowed;
  opacity: 0.5;
}
</style>
