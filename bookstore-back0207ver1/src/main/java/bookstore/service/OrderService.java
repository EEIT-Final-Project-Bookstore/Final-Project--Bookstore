package bookstore.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookstore.domain.CartBean;
import bookstore.domain.CartItemBean;
import bookstore.domain.CouponBean;
import bookstore.domain.IdGenerator;
import bookstore.domain.OrderBean;
import bookstore.domain.OrderDetailBean;
import bookstore.domain.PaymentMethodsBean;
import bookstore.domain.StatusBean;
import bookstore.dto.CartItemDto;
import bookstore.dto.OrderDetailResponseDTO;
import bookstore.dto.OrderResponseDTO;
import bookstore.repository.CartRepository;
import bookstore.repository.CouponRepository;
import bookstore.repository.OrderDetailRepository;
import bookstore.repository.OrderRepository;
import bookstore.repository.PaymentMethodRepository;
import bookstore.repository.StatusesRepository;

@Service
@Transactional
public class OrderService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private CartService cartService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private StatusesRepository statusRepository;

    // 1.新增1筆訂單
    public OrderResponseDTO createOrder(Integer cartId, String couponCode) {
        CartBean cart = cartService.getCartById(cartId);
        // 檢查購物車是否有效
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("購物車為空，無法生成訂單。");
        }
        // 創建訂單
        OrderBean order = new OrderBean();
        order.setOrderId(idGenerator.generateId());
        order.setCustomer(cart.getCustomer());
        order.setOrderCreationTime(LocalDateTime.now());
        // // 生成發票號碼 -> 移到EcpayController (應是付款成功後才開立)
        // String invoiceNumber = InvoiceNumberGenerator.generateInvoiceNumber();
        // order.setInvoiceNumber(invoiceNumber);
        // order.setIssuedTime(LocalDateTime.now());
        // 設定付款方式為 "信用卡"
        PaymentMethodsBean paymentMethod = paymentMethodRepository.findByPaymentMethodName("信用卡");
        if (paymentMethod == null) {
            throw new IllegalArgumentException("支付方式 '信用卡' 不存在");
        }
        order.setPaymentMethod(paymentMethod);
        // 設置購物車狀態為「已清空」
        StatusBean unpaidStatus = statusRepository.findByDetailedStatus("待付款");
        order.setStatus(unpaidStatus);
        // 查找顧客的購物車內容
        // List<CartItemDto> cartItems = cartService.getCartItemDtos(cartId);
        // 處理優惠券(a-c):
        // a.計算原始金額
        int originalTotal = calculateCartTotal(cart);
        order.setTotalAmount(originalTotal);
        // b.應用優惠券
        int discountAmount = 0;
        CouponBean appliedCoupon = null;
        if (couponCode != null && !couponCode.isEmpty()) {
            CouponBean coupon = couponRepository.findByCouponCode(couponCode);
            if (coupon != null) {
                discountAmount = coupon.getDiscount();
                appliedCoupon = coupon;
            }
            order.setCoupon(appliedCoupon);
        }
        // c.計算最終金額
        int finalAmount = originalTotal - discountAmount;
        order.setFinalAmount(finalAmount);
        // System.out.println("折扣後的最終金額: " + order.getFinalAmount());
        order.setLastUpdatedTime(LocalDateTime.now());
        orderRepository.save(order);
        // 設定訂單明細
        for (CartItemBean item : cart.getItems()) {
            OrderDetailBean detail = new OrderDetailBean();
            detail.setOrder(order);
            detail.setBook(item.getBook());
            detail.setQuantity(item.getQuantity());
            detail.setSubtotal(item.getQuantity() * item.getBook().getPrice());
            orderDetailRepository.save(detail);
        }
        // 清空購物車
        cartService.clearCart(cart.getCustomer().getCustomerID());
        cart.getItems().clear(); // 清空購物車中的所有商品
        cartRepository.save(cart); // 保存清空後的購物車
        // 包裝結果
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(order.getOrderId());
        responseDTO.setFinalAmount(finalAmount);
        responseDTO.setMessage("訂單創建成功");
        return responseDTO;
    }

    // 計算購物車總金額(未扣除折扣)
    public int calculateCartTotal(CartBean cart) {
        int total = 0;
        for (CartItemBean item : cart.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // 2.查詢顧客所有訂單
    public List<Map<String, Object>> getAllOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomer_CustomerIDOrderByOrderCreationTimeDesc(customerId)
                .stream()
                .map(order -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("orderId", order.getOrderId());
                    result.put("detailedStatus", order.getStatus().getDetailedStatus()); // 狀態名稱
                    result.put("orderCreationTime", order.getOrderCreationTime().toLocalDate()); // 訂單建立時間
                    result.put("finalAmount", order.getFinalAmount()); // 最終金額
                    result.put("invoiceNumber", order.getInvoiceNumber()); // 發票號碼
                    return result;
                })
                .toList();
    }

    // 3.查詢顧客訂單明細
    public OrderDetailResponseDTO getOrderDetailById(String orderId) {
        OrderBean order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("找不到訂單"));
        List<OrderDetailBean> orderDetails = orderDetailRepository.findByOrder(order);
        List<CartItemDto> items = orderDetails.stream()
                .map(detail -> new CartItemDto(
                        detail.getBook().getImageUrl(), // 書籍圖片
                        detail.getBook().getBookName(),
                        detail.getQuantity(),
                        detail.getBook().getPrice(),
                        detail.getSubtotal()))
                .collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return new OrderDetailResponseDTO(
                order.getOrderId(),
                order.getStatus().getDetailedStatus(),
                order.getOrderCreationTime().format(formatter),
                order.getFinalAmount(),
                order.getInvoiceNumber(),
                items);
    }

    public Optional<OrderBean> getOrderById(String orderID) {
        return orderRepository.findByOrderId(orderID);
    }

    // 使用 orderRepository 將更新後的對象保存到資料庫中
    public OrderBean updateOrder(OrderBean order) {
        return orderRepository.save(order);
    }
}
