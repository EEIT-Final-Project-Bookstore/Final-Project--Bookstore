<template>
  <div class="container mx-auto py-4">
    <!-- 查詢區域 -->
    <div class="mb-6 flex items-center space-x-4 justify-center">
      <label for="customerId" class="font-semibold">顧客ID：</label>
      <input
        id="customerId"
        v-model="customerId"
        type="text"
        placeholder="請輸入顧客ID"
        class="border rounded px-2 py-1"
      />
      <button
        @click="getOrders"
        class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
      >
        查詢該顧客訂單
      </button>
      <button
        @click="getAllOrders"
        class="px-4 py-2 bg-gray-600 text-white rounded-md hover:bg-gray-700"
      >
        查詢所有訂單
      </button>
    </div>

    <!-- 訂單列表 -->
    <table class="w-full table-auto border-collapse border border-gray-300">
      <thead class="bg-blue-500 text-white">
        <tr>
          <th class="border border-gray-300 px-4 py-2">訂單編號</th>
          <th class="border border-gray-300 px-4 py-2">顧客ID</th>
          <th class="border border-gray-300 px-4 py-2">狀態</th>
          <th class="border border-gray-300 px-4 py-2">建立日期</th>
          <th class="border border-gray-300 px-4 py-2">操作</th>
        </tr>
      </thead>
      <tbody>
        <!-- 假設後端回傳的每筆訂單資料包含 { orderId, customerId, status, orderCreationTime } -->
        <tr v-for="order in orders" :key="order.orderId" class="hover:bg-gray-100">
          <td class="border border-gray-300 px-4 py-2 text-center">
            {{ order.orderId }}
          </td>
          <td class="border border-gray-300 px-4 py-2 text-center">
            {{ order.customerId }}
          </td>
          <td class="border border-gray-300 px-4 py-2 text-center">
            {{ order.status }}
          </td>
          <td class="border border-gray-300 px-4 py-2 text-center">
            {{ order.orderCreationTime }}
          </td>
          <td class="border border-gray-300 px-4 py-2 text-center">
            <div class="flex items-center justify-center space-x-2">
              <!-- 查看明細 -->
              <button
                @click="getOrderDetail(order.orderId)"
                class="px-2 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700"
              >
                查看明細
              </button>

              <!-- 只有「待付款」狀態才允許取消 -->
              <button
                v-if="order.status === '待付款'"
                @click="cancelOrder(order.orderId)"
                class="px-2 py-1 bg-red-600 text-white rounded-md hover:bg-red-700"
              >
                取消訂單
              </button>
              <span v-else class="text-gray-500">不可取消</span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 分頁按鈕 -->
    <div class="mt-4 flex justify-center items-center space-x-4">
      <button
        @click="prevPage"
        :disabled="page <= 0"
        class="px-4 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600 disabled:opacity-50"
      >
        上一頁
      </button>
      <span>目前頁數：{{ page + 1 }} / {{ totalPages }}</span>
      <button
        @click="nextPage"
        :disabled="page >= totalPages - 1"
        class="px-4 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600 disabled:opacity-50"
      >
        下一頁
      </button>
    </div>

    <!-- 訂單明細顯示區 -->
    <div v-if="selectedOrderDetail" class="mt-8 p-4 bg-white rounded shadow">
      <h2 class="text-xl font-bold mb-4">訂單明細</h2>
      <p class="mb-2"><strong>訂單編號：</strong>{{ selectedOrderDetail.orderId }}</p>
      <p class="mb-2"><strong>顧客ID：</strong>{{ selectedOrderDetail.customerId }}</p>
      <p class="mb-2"><strong>狀態：</strong>{{ selectedOrderDetail.status }}</p>
      <p class="mb-2">
        <strong>建立日期：</strong>{{ selectedOrderDetail.orderCreationTime }}
      </p>
      <p class="mb-2"><strong>應付金額：</strong>{{ selectedOrderDetail.finalAmount }}</p>

      <!-- 商品列表 -->
      <div v-if="selectedOrderDetail.details && selectedOrderDetail.details.length">
        <h3 class="text-lg font-semibold mt-4 mb-2">商品列表</h3>
        <table class="w-full table-auto border-collapse border border-gray-300">
          <thead class="bg-blue-500 text-white">
            <tr>
              <th class="border border-gray-300 px-4 py-2">明細ID</th>
              <th class="border border-gray-300 px-4 py-2">書籍ID</th>
              <th class="border border-gray-300 px-4 py-2">數量</th>
              <th class="border border-gray-300 px-4 py-2">小計</th>
            </tr>
          </thead>
          <tbody>
            <!-- 假設 each detail 格式: { detailId, bookId, quantity, subtotal } -->
            <tr
              v-for="item in selectedOrderDetail.details"
              :key="item.detailId"
              class="hover:bg-gray-100"
            >
              <td class="border border-gray-300 px-4 py-2 text-center">
                {{ item.detailId }}
              </td>
              <td class="border border-gray-300 px-4 py-2 text-center">
                {{ item.bookId }}
              </td>
              <td class="border border-gray-300 px-4 py-2 text-center">
                {{ item.quantity }}
              </td>
              <td class="border border-gray-300 px-4 py-2 text-center">
                {{ item.subtotal }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axiosapi from "@/plugins/axios"; // ✅ 改為自訂 axios 實例

export default {
  name: "OrderManagement",
  data() {
    return {
      customerId: "", // 用來輸入查詢的顧客 ID
      orders: [], // 存放查詢回來的訂單列表
      page: 0, // 當前頁碼（後端分頁用）
      size: 5, // 每頁筆數（可自行調整）
      totalPages: 0, // 紀錄後端回傳的總頁數
      selectedOrderDetail: null, // 用來存放「目前選中的訂單明細」
    };
  },
  methods: {
    // 1. 查詢所有訂單 (假設後端能接受 customerId = 0 代表查所有)
    getAllOrders() {
      axiosapi
        .post(`/api/order/list/0`, {
          page: this.page,
          size: this.size,
        })
        .then((response) => {
          // 回傳若為 Page 物件，通常會包含 content, totalPages 等
          this.orders = response.data.content || [];
          this.totalPages = response.data.totalPages || 1;
          // 清空已選明細
          this.selectedOrderDetail = null;
        })
        .catch((error) => {
          console.error("取得所有訂單失敗：", error);
          alert("取得所有訂單失敗");
        });
    },

    // 2. 根據顧客 ID 查詢該顧客訂單
    getOrders() {
      if (!this.customerId) {
        alert("請輸入顧客ID");
        return;
      }
      axiosapi
        .post(`/api/order/list/${this.customerId}`, {
          page: this.page,
          size: this.size,
        })
        .then((response) => {
          this.orders = response.data.content || [];
          this.totalPages = response.data.totalPages || 1;
          // 清空已選明細
          this.selectedOrderDetail = null;
        })
        .catch((error) => {
          console.error("取得顧客訂單失敗：", error);
          alert("取得顧客訂單失敗");
        });
    },

    // 3. 取消單筆訂單
    cancelOrder(orderId) {
      if (!confirm(`確定要取消訂單 ${orderId} 嗎？`)) {
        return;
      }
      axiosapi
        .post(`/api/order/cancel/${orderId}`)
        .then((response) => {
          alert(response.data.message);
          // 刷新當前列表
          this.getOrders();
        })
        .catch((error) => {
          console.error("取消訂單失敗：", error);
          alert("取消訂單失敗");
        });
    },

    // 4. 取得單筆訂單明細
    getOrderDetail(orderId) {
      axiosapi
        .get(`/api/order/detail/${orderId}`)
        .then((response) => {
          this.selectedOrderDetail = response.data;
        })
        .catch((error) => {
          console.error("取得訂單明細失敗：", error);
          alert("取得訂單明細失敗");
        });
    },

    // 分頁 - 上一頁
    prevPage() {
      if (this.page > 0) {
        this.page--;
        // 若是先前查詢特定顧客，就呼叫 getOrders
        // 否則呼叫 getAllOrders
        if (this.customerId) {
          this.getOrders();
        } else {
          this.getAllOrders();
        }
      }
    },

    // 分頁 - 下一頁
    nextPage() {
      if (this.page < this.totalPages - 1) {
        this.page++;
        if (this.customerId) {
          this.getOrders();
        } else {
          this.getAllOrders();
        }
      }
    },
  },
};
</script>

<style scoped>
/* =====================================
   ✅ 仿照 A 範例的 Tailwind + 自訂樣式 
   ===================================== */

/* 容器 */
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

/* 標題 */
h1 {
  color: #333;
}

/* 表格 */
table {
  border-collapse: collapse;
  background: white;
  border-radius: 8px;
  overflow: hidden;
}

/* 表格標題列 */
thead {
  background-color: #3b82f6; /* Tailwind "bg-blue-500" */
  color: white;
}

/* 邊框、置中 */
th,
td {
  text-align: center;
  border: 1px solid #ddd;
}

/* 奇偶行交錯 */
tbody tr:nth-child(even) {
  background: #f9fafb;
}
tbody tr:nth-child(odd) {
  background: #ffffff;
}

/* 按鈕特效(滑鼠移入放大、按下縮小) */
button {
  transition: transform 0.2s ease-in-out;
}
button:hover {
  transform: scale(1.05);
}
button:active {
  transform: scale(0.95);
}

/* 訂單明細區塊 */
.order-detail {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
}
</style>
