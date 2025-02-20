<template>
  <div class="admin-container">
    <!-- <header class="header">
      <div class="header-content">
        <h1><i class="fas fa-image"></i> 首頁圖片管理</h1>
      </div>
    </header> -->


    <div class="main-content">
      <!-- 查詢區 -->
      <div class="card">
        <div class="filter-section">
          <div class="search-box">
            <i class="fas fa-search search-icon"></i>
            <input
              v-model="searchBookId"
              type="text"
              placeholder="請輸入書籍 ID"
              class="input short-input search-input"
              @keyup.enter="fetchSingleImage"
            />
          </div>
          <div class="button-group">
            <button @click="fetchSingleImage" class="btn btn-primary">查詢圖片</button>
            <button @click="fetchImages(true)" class="btn btn-secondary">顯示全部</button>
          </div>
        </div>
      </div>


      <!-- 新增圖片 -->
      <!-- 新增圖片區域 -->
      <div class="card">
        <div class="add-image-form">
          <label>書籍 ID：</label>
          <input
            v-model="newImage.bookId"
            type="text"
            class="input short-input"
            required
          />
          <label>圖片 ID：</label>
          <input
            v-model="newImage.imageID"
            type="text"
            class="input short-input"
            @keyup.enter="submitNewImage"
            required
          />
          <button @click="submitNewImage" class="btn btn-primary">新增圖片</button>
        </div>
      </div>


      <!-- 圖片列表 -->
      <div class="card table-card" v-if="images.length">
        <div class="table-responsive">
          <table>
            <thead>
              <tr>
                <th>流水號</th>
                <th>圖片 ID</th>
                <th>書籍 ID</th>
                <th>圖片</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="image in images" :key="image.id">
                <td>{{ image.id }}</td>
                <td>{{ image.imageID }}</td>
                <td>{{ image.bookId }}</td>
                <td>
                  <img :src="image.url" :alt="image.bookName" width="300" height="300" />
                </td>
                <td>
                  <button @click="deleteImage(image.bookId)" class="btn btn-danger">
                    刪除
                  </button>
                  <button @click="openEditModal(image)" class="btn btn-icon">
                    <i class="fas fa-edit"></i> 編輯
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- 編輯圖片 Modal -->
        <div v-if="showEditModal" class="modal-overlay">
          <div class="modal">
            <div class="modal-header">
              <h3>編輯圖片</h3>
              <button @click="closeEditModal" class="btn-close">
                <i class="fas fa-times"></i>
              </button>
            </div>
            <div class="modal-body">
              <label>書籍 ID</label>
              <input
                v-model="editImageData.bookId"
                type="text"
                class="input"
                required
                disabled
              />


              <label>圖片 ID</label>
              <input v-model="editImageData.imageID" type="text" class="input" required />


              <label>圖片 URL</label>
              <input v-model="editImageData.url" type="text" class="input" required  disabled/>
            </div>
            <div class="modal-footer">
              <button @click="submitEditImage" class="btn btn-primary">保存</button>
              <button @click="closeEditModal" class="btn btn-secondary">取消</button>
            </div>
          </div>
        </div>
      </div>


      <div v-else class="card empty-state">
        <p>沒有圖片</p>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, reactive, onMounted } from "vue";
import axiosapi from "@/plugins/axios";
import swal from "sweetalert2";
const images = ref([]);
const searchBookId = ref("");


const newImage = reactive({
  bookId: "",
  imageID: "",
});


//查詢圖片數據
const fetchImages = async (showAll = false) => {
  try {
    const params = showAll ? {} : { bookId: searchBookId.value };
    console.log("📢 發送 API 請求: 獲取圖片數據，參數:", params);
    const response = await axiosapi.get("/api/homepage/all", { params });
    if (!response || !response.data) {
      console.warn("⚠️ API 返回的數據為空:", response);
      images.value = [];
      return;
    }
    if (!Array.isArray(response.data)) {
      console.warn("⚠️ API 返回的數據不是陣列:", response.data);
      images.value = [];
      return;
    }
    images.value = response.data;
    console.log("✅ 獲取到的圖片數據:", images.value);
  } catch (error) {
    console.error("❌ 獲取圖片數據時發生錯誤:", error);
  }
};


const fetchSingleImage = async () => {
  try {
    let params = {};
    if (!searchBookId.value.trim()) {
      console.warn("⚠️ 請輸入書籍 ID 後再查詢");
      images.value = [];
      return;
    }
    params.bookId = searchBookId.value.trim();
    const url = "/api/homepage/books/image-by-book";
    console.log("📢 發送單筆查詢 API 請求:", url, params);
    const response = await axiosapi.get(url, { params });
    console.log("✅ 單筆查詢 API 回應:", response.data);
    if (!response || !response.data) {
      console.warn("⚠️ API 返回的數據為空:", response);
      images.value = [];
      return;
    }
    if (!Array.isArray(response.data)) {
      console.warn("⚠️ API 返回的數據不是陣列，轉為陣列處理:", response.data);
      images.value = [response.data]; // 如果 API 返回單個物件，轉為陣列
      return;
    }
    images.value = response.data;
    console.log("✅ 單筆查詢結果:", images.value);
  } catch (error) {
    console.error("❌ 獲取單筆圖片數據時發生錯誤:", error);
    swal.fire({
      icon: "error",
      title: "圖片查詢失敗！",
      text: "首頁資料庫無該書籍圖片",
     
    });
  }
};


//刪除圖片
const deleteImage = async (bookId) => {
  try {
    console.log(`🗑 發送刪除請求到 /api/homepage/delete-by-book，書籍ID: ${bookId}`);
    const response = await axiosapi.delete(
      `/api/homepage/delete-by-book?bookId=${bookId}`
    );
    console.log("✅ 刪除成功:", response.data);
    fetchImages(); // 刪除後刷新列表
  } catch (error) {
    console.error("❌ 刪除圖片失敗:", error);
  }
};


const showEditModal = ref(false);
const editImageData = reactive({
  id: "",
  bookId: "",
  imageID: "",
  url: "",
});


// 打開編輯 Modal
const openEditModal = (image) => {
  editImageData.id = image.id;
  editImageData.bookId = image.bookId;
  editImageData.imageID = image.imageID;
  editImageData.url = image.url;
  showEditModal.value = true;
};


// 關閉編輯 Modal
const closeEditModal = () => {
  showEditModal.value = false;
};


// 提交編輯後的數據
const submitEditImage = async () => {
  try {
    const requestData = {
      imageID: Number(editImageData.imageID),
      book: { bookId: Number(editImageData.bookId) },
      url: editImageData.url,
    };


    console.log("📢 提交的編輯數據:", requestData);
    const response = await axiosapi.put(
      `/api/homepage/update-by-book?bookId=${editImageData.bookId}`,
      requestData
    );
    console.log("✅ 更新圖片成功:", response.data);


    swal.fire({
      icon: "success",
      title: "圖片更新成功！",
      text: `書籍ID: ${editImageData.bookId}, 圖片ID: ${editImageData.imageID}`,
      timer: 2000,
      showConfirmButton: false,
    });


    closeEditModal();
    fetchImages();
  } catch (error) {
    console.error("❌ 更新圖片失敗", error);


    swal.fire({
      icon: "error",
      title: "圖片更新失敗！",
      text: `圖片 ID:  ${editImageData.imageID} 已存在`,
     
    });
  }
};


//新增圖片
const submitNewImage = async () => {
  try {
    if (!newImage.bookId || !newImage.imageID) {
      swal.fire({
        icon: "warning",
        title: "請填寫必要資訊",
        text: "書籍 ID 和 圖片 ID 不能為空",
      });
      return;
    }


    // 檢查書籍 ID 是否已經存在
    const existingImage = images.value.find(
      (image) => image.bookId === Number(newImage.bookId)
    );
    if (existingImage) {
      swal.fire({
        icon: "error",
        title: "新增失敗",
        text: `書籍 ID: ${newImage.bookId} 已經有圖片，請修改或刪除現有圖片後再新增。`,
      });
      return;
    }
    const requestData = {
      imageID: Number(newImage.imageID),
      book: { bookId: Number(newImage.bookId) },
    };
    console.log("📢 提交的數據:", requestData);
    const response = await axiosapi.post("/api/homepage/add", requestData);
    console.log("✅ 新增圖片成功:", response.data);


    swal.fire({
      icon: "success",
      title: "圖片新增成功！",
      text: `書籍 ID: ${newImage.bookId}, 圖片 ID: ${newImage.imageID}`,
      timer: 2000,
      showConfirmButton: false,
    });


    // 清空輸入欄位並刷新列表
    newImage.bookId = "";
    newImage.imageID = "";
    fetchImages();
  } catch (error) {
    console.error("❌ 新增圖片失敗", error);


    swal.fire({
      icon: "error",
      title: "圖片新增失敗！",
      text: "請檢查書籍 ID和圖片ID",
    });
  }
};


//組件掛載時自動獲取數據
onMounted(() => fetchImages(true));
</script>


<style scoped>
.admin-container {
  /* min-height: 100vh; */
  background-color: #f3f4f6;
}
.header {
  background-color: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
.header h1 {
  font-size: 1.5rem;
  font-weight: 600;
  color: #111827;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.main-content {
  max-width: 1600px;
  margin: auto;
  margin-top: 10px;
}
.card {
  width: 1000px;
  background: white;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}
.search-box input {
  font-size: 16px;
}
.button-group {
  margin-top: 20px;
}
.table-responsive {
  overflow-x: auto;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th {
  background-color: #f9fafb;
  padding: 0.75rem 1rem;
  text-align: left;
  font-weight: 500;
  color: black;
  text-transform: uppercase;
  font-size: 16px;
}
td {
  padding: 1rem;
  font-size: 18px;
  border-bottom: 1px solid #e5e7eb;
}
td img {
  width: 100px; /* 調整大小 */
  height: 150px;
  object-fit: contain; /* 避免圖片變形 */
  border-radius: 8px; /* 圓角 */
  display: block;
  margin: auto;
}
.table-responsive {
  overflow-x: auto;
  max-width: 100%;
}
th,
td {
  text-align: center;
  vertical-align: middle;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
 
}
.modal {
  background: white;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  max-width: 450px;
  max-height: 80vh;
  overflow-y: auto;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.3);
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}
.modal-body {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.modal-footer {
  padding: 1rem 1.5rem;
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
  border-top: 1px solid #e5e7eb;
}
.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 0.375rem;
  font-weight: 500;
  font-size: 15px;
  transition: all 0.2s;
  cursor: pointer;
  border: none;
}
.btn-primary {
  background-color: #3b82f6;
  color: white;
}
.btn-danger {
  background-color: #dc3545;
  color: white;
}
.btn-secondary {
  background-color: #f3f4f6;
  color: #374151;
}
.btn-close {
  background: none;
  border: none;
  color: #6b7280;
  cursor: pointer;
}
.add-image-form {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
}
</style>
