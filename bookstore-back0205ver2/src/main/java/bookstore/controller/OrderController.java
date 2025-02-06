package bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookstore.dto.OrderDetailResponseDTO;
import bookstore.dto.OrderRequestDTO;
import bookstore.dto.OrderResponseDTO;
import bookstore.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 1.創建訂單
    @PostMapping("/create")
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            // 從請求中獲取 cartId 和優惠券代碼
            Integer cartId = orderRequest.getCartId();
            String couponCode = orderRequest.getCouponCode();
            // 呼叫 OrderService 來創建訂單
            OrderResponseDTO response = orderService.createOrder(cartId, couponCode);
            return response;
        } catch (IllegalArgumentException e) {
            // 錯誤處理
            return new OrderResponseDTO("訂單創建失敗: " + e.getMessage());
        }
    }

    // 2.查詢顧客所有訂單
    @GetMapping("/list/{customerId}")
    public List<Map<String, Object>> getCustomerOrders(@PathVariable Long customerId) {
        return orderService.getAllOrdersByCustomer(customerId);
    }

    // 3.查詢訂單明細
    @GetMapping("/detail/{orderId}")
    public OrderDetailResponseDTO getOrderDetail(@PathVariable String orderId) {
        return orderService.getOrderDetailById(orderId);
    }
}
