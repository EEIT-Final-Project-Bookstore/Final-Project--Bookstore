<template>
  <div class="homepage">
    <!-- Header -->
    <header class="main-header">
      <div class="header-overlay">
        <div class="header-content">
          <div class="logo">三民網路書店</div>
          <nav>
            <ul class="nav-links">
              <li><a href="#" @click.prevent="showLogin">會員登入</a></li>
              <li><a href="#">會員專區</a></li>
              <li><a href="#">購物車</a></li>
              <li><a href="#">通知</a></li>
            </ul>
          </nav>
          <SearchPage />
        </div>
      </div>
      <img class="header-image" src="/logo.png" alt="Bookstore Header" />
    </header>

    <!-- Body -->
    <main>
      <!-- Carousel -->
      <div class="carousel-container">
        <!-- 圖片列表 -->
        <div
          class="images-wrapper"
          :style="{
            transform: `translateX(${-currentOffset}px)`,
            transition: smoothTransition ? 'transform 0.5s ease-in-out' : 'none',
          }"
        >
          <!-- 克隆的最後一張圖片 -->
          <img :src="images[images.length - 1]" alt="Cloned Last Image" />
          <!-- 正常圖片 -->
          <img v-for="(image, idx) in images" :key="idx" :src="image" alt="Carousel Image" />
          <!-- 克隆的第一張圖片 -->
          <img :src="images[0]" alt="Cloned First Image" />
        </div>

        <!-- 分頁點 -->
        <ul class="pages">
          <li
            v-for="(image, idx) in images"
            :key="idx"
            :class="{ active: idx === currentIndex }"
            @click="moveTo(idx)"
          ></li>
        </ul>

        <!-- 左右箭頭 -->
        <button class="arrow left" @click="prevSlide">‹</button>
        <button class="arrow right" @click="nextSlide">›</button>
      </div>

      <!-- Top Books -->
      <section class="top-books">
  <h2>暢銷書籍 Top 10</h2>
  <ul class="book-list">
    <li v-for="book in topBooks" :key="book.id" class="book-card">
      <img :src="book.image" :alt="book.title" />
      <h3>{{ book.title }}</h3>
      <span class="price">價格: ${{ book.price }}</span>
    </li>
  </ul>
</section>

    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";
import SearchPage from "../components/SearchPage.vue";


// 圖片數據
const images = ref([
  "../public/j1.jpg",
  "../public/j2.jpg",
  "../public/j3.jpg",
  "../public/j4.jpg",
  "../public/j5.jpg",
]);

const currentIndex = ref(0); // 當前圖片索引
const currentOffset = ref(0); // 當前偏移量
const smoothTransition = ref(true); // 是否啟用平滑過渡
const imageWidth = 800; // 單張圖片的寬度
let timer = null; // 計時器

// 初始化偏移量
const updateOffset = () => {
  currentOffset.value = (currentIndex.value + 1) * imageWidth; // 加 1 是因為有克隆的第一張圖片
};

// 下一張圖片
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

// 上一張圖片
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

// 點擊分頁點
const moveTo = (index) => {
  smoothTransition.value = true;
  currentIndex.value = index;
  updateOffset();
};

// 暢銷書籍數據
const topBooks = ref([]);

// 從後端 API 獲取數據
// 動態加載書籍資訊
const fetchBookDetails = async (bookId) => {
  try {
    const response = await fetch(`/api/book/${bookId}`);
    return await response.json(); // 返回書籍詳情
  } catch (error) {
    console.error(`無法加載書籍 ID ${bookId} 的詳情`, error);
    return null;
  }
};

const fetchTopBooks = async () => {
  try {
    const response = await fetch("/api/homepage/all");
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // 並行請求書籍詳情
    const booksWithDetails = await Promise.all(
      data.map(async (item) => {
        const details = await fetchBookDetails(item.bookId);
        return {
          
          image: item.url,
          title: details?.BookName || `書籍 ID: ${item.bookId}`,
          price: details?.Price || `價格未知`,
        };
      })
    );

    topBooks.value = booksWithDetails;
  } catch (error) {
    console.error("無法獲取首頁書籍數據", error);
  }
};


onMounted(() => {
  updateOffset();
  timer = setInterval(nextSlide, 3000);

  // 加載首頁數據
  fetchTopBooks();
});

onUnmounted(() => {
  clearInterval(timer);
});
</script>

<style scoped>
.carousel-container {
  position: relative;
  width: 800px; /* 單張圖片寬度 */
  height: 400px; /* 單張圖片高度 */
  margin: 0 auto;
  overflow: hidden; /* 限制圖片只顯示一部分 */
}

.images-wrapper {
  display: flex;
  transition: transform 0.5s ease-in-out; /* 平滑過渡效果 */
}

.images-wrapper img {
  width: 800px;
  height: 400px;
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
  background-color: white;
}

.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
}

.arrow.left {
  left: 10px;
}

.arrow.right {
  right: 10px;
}

.book-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  margin: 20px;
  list-style: none;
  padding: 0;
}

.book-card {
  width: 200px;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 10px;
  text-align: center;
  background: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.book-card img {
  max-width: 100%;
  height: auto;
  margin-bottom: 10px;
}

.book-card h3 {
  font-size: 16px;
  margin: 5px 0;
}

.book-card p {
  font-size: 14px;
  color: #666;
  margin: 5px 0 10px;
}

.book-card .price {
  font-weight: bold;
  color: #800040;
}
</style>
