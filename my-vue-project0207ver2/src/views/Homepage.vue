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
    </header>

    <!-- Body -->
    <main>
      <!-- Carousel -->
      <div class="carousel-container">
        <div
          v-if="images && images.length"
          class="images-wrapper"
          :style="{
            transform: `translateX(${-currentOffset}px)`,
            transition: smoothTransition ? 'transform 0.5s ease-in-out' : 'none',
          }"
        >
          <img :src="images[images.length - 1]" alt="Cloned Last Image" />
          <img v-for="(image, idx) in images" :key="idx" :src="image" alt="Carousel Image" />
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
        <button class="arrow left" @click="prevSlide">‹</button>
        <button class="arrow right" @click="nextSlide">›</button>
      </div>

      <!-- Top Books -->
      <section class="top-books">
        <h2>暢銷書籍 Top 10</h2>
        <ul v-if="topBooks && topBooks.length" class="book-list">
          <li v-for="book in topBooks" :key="book.id" class="book-card">
            <img :src="book.image" :alt="book.title" />
            <h3>{{ book.title }}</h3>
            <span class="price">價格: {{ book.price }}</span>
          </li>
        </ul>
        <p v-else>暢銷書籍加載中...</p>
      </section>
    </main>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";

export default {
  setup() {
    const images = ref(["/j1.jpg", "/j2.jpg", "/j3.jpg", "/j4.jpg", "/j5.jpg"]);
    const currentIndex = ref(0);
    const currentOffset = ref(0);
    const smoothTransition = ref(true);
    const imageWidth = 800;
    let timer = null;

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
        const baseUrl = import.meta.env.VITE_API_URL || "http://localhost:8080/api"; // Default fallback URL
        const response = await fetch(`${baseUrl}/homepage/top10`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const data = await response.json();

        console.log("從後端獲取的原始數據:", data);

        // 格式化數據
        topBooks.value = data.map((item) => ({
          id: item.imageID,
          image: item.url || "https://via.placeholder.com/150",
          title: item.bookName || "無書名",
          price: item.price !== null ? `NT$${item.price}` : "價格未知",
        }));

        console.log("格式化後的暢銷書籍數據:", JSON.parse(JSON.stringify(topBooks.value)));
      } catch (error) {
        console.error("無法獲取首頁書籍數據", error);
      }
    };

    onMounted(() => {
      updateOffset();
      timer = setInterval(nextSlide, 3000);
      fetchTopBooks();
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
    };
  },
};
</script>

<style scoped>
/* 同样的样式代码 */
</style>


<style scoped>
.carousel-container {
  position: relative;
  width: 800px;
  height: 400px;
  margin: 0 auto;
  overflow: hidden;
}

.images-wrapper {
  display: flex;
  transition: transform 0.5s ease-in-out;
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

.book-card .price {
  font-weight: bold;
  color: #800040;
}
</style>
