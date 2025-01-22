import axios from "axios";
import axiosInstance from './axios'

//獲取所有圖片數據
export const fetchAllHomepageImages = async (params = {}) => {
    try {
        console.log("發送請求到 /all，參數:", params);
        const response = await axiosInstance.get("/all", { params }); // 可選傳參，如 { bookId: 123 }
        console.log("後端返回的數據:", response.data); // 確認返回數據
        return response.data;
    } catch (error) {
        console.error("獲取圖片數據失敗:", error);
        throw error; // 向外拋出錯誤以便調用方處理
    }
};

//查詢單筆圖片數據
export const fetchHomepageImagesBookId = async (bookId) => {
    try {
        console.log("發送請求到/books/image-by-book，書籍ID:", bookId);
        const response = await axiosInstance.get(`/books/image-by-book`, {
            params: { bookId },
        })
        console.log("後端返回的數據:", response.data)
        return response.data
    } catch {
        console.error("查詢圖片數據失敗", error)
        throw error;
    }
}

// 新增圖片
export const addHomepageImage = async (data) => {
    try {
        const response = await axiosInstance.post("/add", data);
        return response.data;
    } catch (error) {
        console.error("新增圖片失敗:", error);
        throw error;
    }
};

// 更新圖片
export const updateHomepageImage = async (bookId, data) => {
    try {
        const response = await axiosInstance.put(`/update-by-book?bookId=${bookId}`, data);
        return response.data;
    } catch (error) {
        console.error("更新圖片失敗:", error);
        throw error;
    }
};

// 刪除圖片
export const deleteHomepageImage = async (bookId) => {
    try {
        const response = await axiosInstance.delete(`/delete-by-book?bookId=${bookId}`);
        return response.data;
    } catch (error) {
        console.error("刪除圖片失敗:", error);
        throw error;
    }
};

export const fetchTopBooks = async () => {
    return axios.get(`/api/homepage/top-books`)
}
