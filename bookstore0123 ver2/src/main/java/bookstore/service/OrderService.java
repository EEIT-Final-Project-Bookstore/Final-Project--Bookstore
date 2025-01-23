package bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.domain.OrderBean;
import bookstore.domain.StatusBean;
import bookstore.repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService{
	@Autowired
	private OrderRepository ordersRepository;
public OrderBean getOrderById(Integer orderID) {
	
    return ordersRepository.findById(orderID)
            .orElseThrow(() -> new RuntimeException("未找到訂單，OrderID: " + orderID));
}
/**
 * 確保訂單存在並更新狀態
 *
 * @param orderID    訂單 ID
 * @param statusBean 新的狀態
 * @return 更新後的訂單
 */
@Transactional
public OrderBean updateOrderStatus(Integer orderID, StatusBean statusBean) {
    OrderBean order = getOrderById(orderID); // 查詢訂單
    order.setStatus(statusBean); // 更新狀態
    return ordersRepository.save(order); // 保存變更
}}
