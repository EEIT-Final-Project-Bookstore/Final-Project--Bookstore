import axios from "axios";
const apiClient = axios.create({
    baseURL: import.meta.env.VITE_API_URL,//後端api的基本URL
    timeout: 5000
})
console.log(import.meta.env.VITE_API_URL); // 輸出環境變數的值

export default apiClient;



