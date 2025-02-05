import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/books"; // 🚀 後端 API URL

export const fetchBookDetail = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/${id}`);
        const data = response.data;

        // 🚀 轉換後端 API 回傳格式，使其適配前端
        return {
            id: data.bookId,
            title: data.bookName,
            author: data.authorName, // ✅ 改為直接使用 authorName
            publisher: data.publisherName, // ✅ 改為直接使用 publisherName
            categoryName: data.categoryName, // ✅ 使用 categoryName
            subcategoryName: data.subcategoryName, // ✅ 使用 subcategoryName
            publishDate: data.publishDate,
            stock: data.stock,
            price: data.price,
            description: data.description,
            coverImage: data.imageUrl || "https://via.placeholder.com/150", // 預設圖片
        };
    } catch (error) {
        console.error("獲取書籍資料失敗:", error);
        return null; // 若發生錯誤，回傳 null
    }
};
