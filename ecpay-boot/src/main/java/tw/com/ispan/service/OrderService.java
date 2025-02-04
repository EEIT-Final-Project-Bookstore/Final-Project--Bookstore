package tw.com.ispan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.OrdersBean;
import tw.com.ispan.domain.StatusBean;
import tw.com.ispan.repository.OrdersRepository;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    /**
     * 查詢單筆訂單
     *
     * @param orderID 訂單 ID
     * @return 查詢到的訂單
     */
    public OrdersBean getOrderById(Integer orderID) {
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
    public OrdersBean updateOrderStatus(Integer orderID, StatusBean statusBean) {
        OrdersBean order = getOrderById(orderID); // 查詢訂單
        order.setStatus(statusBean); // 更新狀態
        return ordersRepository.save(order); // 保存變更
    }
}

