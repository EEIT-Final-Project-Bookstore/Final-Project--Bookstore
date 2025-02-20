<template>
  <div class="homepage">
    <div class="carousel-containerScam">
      <!-- 根據 messages 自動生成輪播項目 -->
      <div
        v-for="(msg, index) in messages"
        :key="index"
        :class="['carousel-slide', { active: currentSlide === index }]"
      >
        <h5>{{ msg }}</h5>
      </div>
    </div>
    <main>
      <!-- Carousel -->
      <div class="carousel-container">
        <div
          v-if="images.length"
          class="images-wrapper"
          :style="{
            transform: `translateX(${-currentOffset}px)`,
            transition: smoothTransition ? 'transform 0.5s ease-in-out' : 'none',
          }"
        >
          <img :src="images[images.length - 1]" alt="Cloned Last Image" />
          <img
            v-for="(image, idx) in images"
            :key="idx"
            :src="image"
            alt="Carousel Image"
          />
          <img :src="images[0]" alt="Cloned First Image" />
        </div>
        <ul class="pages">
          <li
            v-for="(image, idx) in images"
            :key="idx"
            :class="{ active: idx === currentIndex }"
            @click="moveTo(idx)"
          ></li>
        </ul>
        <div class="arrow-container">
          <button class="arrow left" @click="prevSlide">‹</button>
          <button class="arrow right" @click="nextSlide">›</button>
        </div>
      </div>

      <!-- Top Books -->
      <section class="top-books">
        <h2>📚 暢銷書籍 Top 10</h2>
        <ul v-if="topBooks.length" class="book-list">
          <li v-for="book in topBooks" :key="book.id" class="book-card">
            <!-- 讓圖片可以點擊，使用 router-link -->
            <router-link :to="{ name: 'book-link', params: { id: book.id } }">
              <img :src="book.image" :alt="book.bookName" />
            </router-link>
            <!-- 讓標題可以點擊 -->
            <h3>
              <router-link
                :to="{ name: 'book-link', params: { id: book.id } }"
                class="book-title"
                style="
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  -webkit-box-orient: vertical;
                  text-overflow: ellipsis;
                  overflow: hidden;
                "
              >
                {{ book.title }}
              </router-link>
            </h3>
            <span class="price">價格: {{ book.price }}</span>
            <div>
              <button class="add-cart-button" @click="addToCart(book.id)">
                加入購物車
              </button>
            </div>
          </li>
        </ul>
        <p v-else class="loading-message">📖 暢銷書籍加載中...</p>
      </section>
    </main>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";
import { useAuthStore } from "@/stores/authStore";
import { useCartStore } from "@/stores/cartStore";
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios";

export default {
  setup() {
    const images = ref(["/j1.jpg", "/j2.jpg", "/j3.jpg", "/j4.jpg", "/j5.jpg"]);
    const currentIndex = ref(0);
    const currentOffset = ref(0);
    const smoothTransition = ref(true);
    const imageWidth = 1000;
    let timer = null;
    const messages = [
      "🔈：防詐原則 (一)不聽信電話通知，要保持冷靜。",
      "🔈：防詐原則 (二)不告知個人資料，要立即查證。",
      "🔈：防詐原則 (三)不依據所留電話查證，要報警處理。",
    ];

    // 目前顯示第幾條訊息
    const currentSlide = ref(0);
    let intervalId = null;

    const updateOffset = () => {
      currentOffset.value = (currentIndex.value + 1) * imageWidth;
    };

    const nextSlide = () => {
      smoothTransition.value = true;
      currentIndex.value++;
      updateOffset();
      if (currentIndex.value >= images.value.length) {
        setTimeout(() => {
          smoothTransition.value = false;
          currentIndex.value = 0;
          updateOffset();
        }, 500);
      }
    };

    const prevSlide = () => {
      smoothTransition.value = true;
      currentIndex.value--;
      updateOffset();
      if (currentIndex.value < 0) {
        setTimeout(() => {
          smoothTransition.value = false;
          currentIndex.value = images.value.length - 1;
          updateOffset();
        }, 500);
      }
    };

    const moveTo = (index) => {
      smoothTransition.value = true;
      currentIndex.value = index;
      updateOffset();
    };

    const topBooks = ref([]);
    const fetchTopBooks = async () => {
      try {
        const baseUrl = import.meta.env.VITE_API_URL;
        const response = await fetch(`${baseUrl}/api/homepage/top10`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const data = await response.json();
        console.log("從後端獲取的原始數據:", data);
        // 格式化數據
        topBooks.value = data.map((item) => {
          // console.log("item", item);
          return {
            id: item.book?.bookId, // 確保這裡用的是書籍 ID
            image:
              item.book?.imageUrl ||
              "https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg",
            title: item.book?.bookName || "無書名",
            price: item.book?.price !== null ? `NT$ ${item.book.price}` : "價格未知",
          };
        });
        console.log(
          "格式化後的暢銷書籍數據:",
          JSON.parse(JSON.stringify(topBooks.value))
        );
      } catch (error) {
        console.error("無法獲取首頁書籍數據", error);
      }
    };
    onMounted(() => {
      updateOffset();
      timer = setInterval(nextSlide, 3000);
      fetchTopBooks();
    });
    // 在元件掛載時，開始自動切換
    onMounted(() => {
      intervalId = setInterval(() => {
        currentSlide.value = (currentSlide.value + 1) % messages.length;
      }, 3000); // 3秒切換一次，可自行調整時間
    });

    // 在元件卸載時，清除計時器
    onUnmounted(() => {
      clearInterval(intervalId);
    });
    onUnmounted(() => {
      clearInterval(timer);
    });

    return {
      images,
      currentIndex,
      currentOffset,
      smoothTransition,
      nextSlide,
      prevSlide,
      moveTo,
      topBooks,
      messages,
      currentSlide,
    };
  },
  methods: {
    async addToCart(id) {
      try {
        const authStore = useAuthStore();
        if (!authStore.customerId) {
          Swal.fire({
            icon: "warning",
            title: "請先登入",
            text: "您需要登入才能加入購物車。",
          });
          return;
        }
        // 發送請求前，檢查庫存是否足夠
        const bookResponse = await axiosapi.get(`/api/books/${id}`);
        if (bookResponse.data?.stock <= 0) {
          Swal.fire({
            icon: "warning",
            title: "庫存不足",
            text: "該商品目前已缺貨。",
          });
          return;
        }
        // console.log("發送請求：", {customerId: authStore.customerId, bookId: id, quantity: 1,});
        const response = await axiosapi.post("/api/cart/add", {
          customerId: authStore.customerId,
          bookId: id,
          quantity: 1,
        });
        if (response.data?.cartId) {
          localStorage.setItem("cartId", response.data.cartId);
          const cartStore = useCartStore();
          await cartStore.fetchCartItems();
          Swal.fire({
            icon: "success",
            title: "成功",
            text: response.data.message,
          });
        } else {
          console.log(response.data.message);
          Swal.fire({
            icon: "error",
            title: "錯誤",
            text: response.data.message || "加入購物車失敗",
          });
        }
      } catch (error) {
        console.error("加入購物車時發生錯誤:", error);
        Swal.fire({
          icon: "error",
          title: "發生錯誤",
          text: "發生錯誤，請稍後再試。",
        });
      }
    },
  },
};
</script>

<style scoped>
.carousel-container {
  position: relative;
  width: 1000px;
  margin: 0 auto;
  overflow: hidden;
}
.images-wrapper {
  width: 100%;
  display: flex;
  transition: transform 0.5s ease-in-out;
}
.images-wrapper img {
  width: 1000px;
  height: 500px;
  object-fit: cover;
  flex-shrink: 0;
}
.pages {
  display: flex;
  justify-content: center;
  list-style: none;
  margin: 10px 0;
  padding: 0;
}
.pages li {
  width: 12px;
  height: 12px;
  background-color: #ccc;
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.pages li.active {
  background-color: rgb(138, 124, 124);
}
.arrow-container {
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  transform: translateY(-50%);
  width: 100%;
}
.arrow {
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
}
.arrow.left {
  left: 10px;
  width: 5%;
}
.arrow.right {
  right: 10px;
  width: 5%;
}
.top-books {
  margin-bottom: 90px;
}
.book-list {
  /* display: flex;
	flex-wrap: wrap;
	justify-content: center;
	gap: 20px;
	margin: 20px;
	list-style: none;
	padding: 0; */
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
}
.book-card {
  /* width: 230px; */
  width: calc(100% / 6 - 20px); /* 每行 5 本書，減去間距 */
  height: 330px; /* 設定統一高度 */
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 10px;
  text-align: center;
  background: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 讓內容平均分布 */
}
.book-card img {
  width: 160px;
  height: 170px; /* 統一圖片高度 */
  object-fit: contain; /* 讓圖片不變形 */
  margin-bottom: 10px;
  margin-top: 10px;
  border-radius: 4px;
}
.book-card h3 {
  margin: 5px 0;
  flex-grow: 1; /* 讓標題填滿剩餘空間 */
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 16px;
}
.book-card .price {
  font-weight: bold;
  color: #800040;
  margin-bottom: 8px;
}
.add-cart-button {
  cursor: pointer;
  font-size: 14px;
  width: 100%;
  background-color: #7318f3;
  color: white;
  border-radius: 6px;
  padding: 6px;
  border: none;
  transition: background-color 0.3s ease;
}
.add-cart-button:hover {
  background-color: #4405d8;
}
.book-title {
  text-decoration: none;
  color: inherit; /* 保持原本的文字顏色 */
}
.book-title:hover {
  text-decoration: underline; /* 滑鼠懸停時加底線 */
}
.carousel-containerScam {
  position: relative;
  width: 500px; /* 可自行調整寬度 */
  height: 30px; /* 可自行調整高度 */
  margin: 40px auto; /* 置中用 */
  margin-top: 20px;
  overflow: hidden;
  border-radius: 8px;
  display: flex;
  border: 1px solid rgb(146, 146, 146);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* 每個輪播項目，預設隱藏 */
.carousel-slide {
  display: none;
  width: 100%;
  text-align: center;
}

/* 顯示中的項目 */
.carousel-slide.active {
  display: block;
}

h5 {
  margin: 0;
  padding: 0;
  font-size: 1.1rem;
  line-height: 1.5;
}
</style>
