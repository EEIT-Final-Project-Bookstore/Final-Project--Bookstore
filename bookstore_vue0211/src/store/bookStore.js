import { defineStore } from "pinia";

export const useBookStore = defineStore("bookStore", {
    state: () => ({
        book: {}, // 儲存當前書籍資料
        isFavorited: false, // 收藏狀態
    }),
    getters: {
        discountedPrice: (state) => {
            // 計算優惠價
            return (state.book.price * (state.book.discount / 100)).toFixed(2);
        },
    },
    actions: {
        setBook(bookData) {
            this.book = bookData; // 設置書籍資料
        },
        toggleFavorite() {
            this.isFavorited = !this.isFavorited; // 切換收藏狀態
        },
    },
});
