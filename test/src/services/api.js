import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080', // 後端 Spring Boot 地址
});

export default {
    createOrder(orderData) {
        return api.post('/orders/create', orderData).then((res) => res.data);
    },
};
