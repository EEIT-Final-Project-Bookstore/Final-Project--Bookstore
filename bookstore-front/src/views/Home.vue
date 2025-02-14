<template>
	<div class="homepage">
		<!-- Header -->
		<header>
			<h1>歡迎來到書店</h1>
		</header>

		<!-- Body -->
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
					<img v-for="(image, idx) in images" :key="idx" :src="image" alt="Carousel Image" />
					<img :src="images[0]" alt="Cloned First Image" />
				</div>
				<ul class="pages">
					<li
						v-for="(image, idx) in images"
						:key="idx"
						:class="{ active: idx === currentIndex }"
						@click="moveTo(idx)"
					>
					</li>
				</ul>
				<button class="arrow left" @click="prevSlide">‹</button>
				<button class="arrow right" @click="nextSlide">›</button>
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
							<router-link :to="{ name: 'book-link', params: { id: book.id } }" class="book-title">
								{{ book.title }}
							</router-link>
						</h3>
						<span class="price">價格: {{ book.price }}</span>
						<div>
							<button class="add-cart-button" @click="addToCart(book.id)">加入購物車</button>
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
				image: item.book?.imageUrl || "https://via.placeholder.com/150",
				title: item.book?.bookName || "無書名",
				price: item.book?.price !== null ? `NT$${item.book.price}` : "價格未知",
			}});
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
	methods: {
		async addToCart (id) {
            try {
				const authStore = useAuthStore();
                if (!authStore.customerId) {
                    Swal.fire({
                        icon: 'warning',
                        title: '請先登入',
                        text: '您需要登入才能加入購物車。',
                    });
                    return;
                }
				console.log("發送請求：", {
					customerId: authStore.customerId,
					bookId: id,
					quantity: 1,
				});
                // if (!this.bookId || isNaN(this.bookId)) {
                //     Swal.fire({
                //         icon: 'error',
                //         title: '錯誤',
                //         text: '無效的書籍 ID',
                //     });
                //     return;
                // }
                const response = await axiosapi.post('/api/cart/add', {
                    customerId: authStore.customerId,
                    bookId: id,
                    quantity: 1,
                });
                if (response.data?.cartId) {
                    localStorage.setItem("cartId", response.data.cartId);
					const cartStore = useCartStore();
					await cartStore.fetchCartItems();
                    Swal.fire({
                        icon: 'success',
                        title: '成功',
                        text: response.data.message,
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '錯誤',
                        text: response.data.message || "加入購物車失敗",
                    });
                }
            } catch (error) {
                console.error("加入購物車時發生錯誤:", error);
                Swal.fire({
                    icon: 'error',
                    title: '發生錯誤',
                    text: '發生錯誤，請稍後再試。',
                });
            }
        },
	}
};
</script>

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
	height: 320px; /* 設定統一高度 */
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
	width: 100%;
	height: 150px; /* 統一圖片高度 */
	object-fit: contain; /* 讓圖片不變形 */
	margin-bottom: 10px;
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

</style>
