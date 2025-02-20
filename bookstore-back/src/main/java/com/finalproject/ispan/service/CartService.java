package com.finalproject.ispan.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finalproject.ispan.domain.BookBean;
import com.finalproject.ispan.domain.CartBean;
import com.finalproject.ispan.domain.CartItemBean;
import com.finalproject.ispan.domain.CustomerBean;
import com.finalproject.ispan.domain.StatusBean;
import com.finalproject.ispan.dto.CartItemDto;
import com.finalproject.ispan.repository.BookRepository;
import com.finalproject.ispan.repository.CartItemRepository;
import com.finalproject.ispan.repository.CartRepository;
import com.finalproject.ispan.repository.CustomerRepository;
import com.finalproject.ispan.repository.StatusRepository;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    
    // 1. 查詢顧客的購物車內容
    public List<CartItemDto> getCartItemDtos(Long customerId) {
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            return new ArrayList<>();  // 如果沒有購物車，返回空列表
        }
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItemBean item : cart.getItems()) {
            // 轉換每個 CartItemBean 為 CartItemDto
            CartItemDto dto = new CartItemDto();
            dto.setBookId(item.getBook().getBookId());
            dto.setBookName(item.getBook().getBookName());
            dto.setImageUrl(item.getBook().getImageUrl());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getBook().getPrice());
            dto.setSubtotal(item.getBook().getPrice() * item.getQuantity());
            cartItemDtos.add(dto);
        }
        return cartItemDtos;  // 返回 List<CartItemDto>
    }

    // 2. 新增書籍至購物車
    public Map<String, Object> addToCart(Long customerId, Integer bookId, Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        // 查詢顧客的購物車
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            cart = new CartBean();
            cart.setCustomerId(customerId);
            cart.setCreationTime(LocalDateTime.now());
            cart.setLastUpdatedTime(LocalDateTime.now());
            cart.setQuantity(0);
            cart.setItems(new ArrayList<>());
            // 查詢顧客資訊並設置
            Optional<CustomerBean> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                cart.setCustomer(customer.get());
            } else {
                response.put("message", "顧客資料不存在");
                return response;
            }
        } else {
            cart.setLastUpdatedTime(LocalDateTime.now());
        }
        // 查詢書籍
        Optional<BookBean> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalBook.isEmpty()) {
            response.put("message", "書籍不存在");
            return response;
        }
        BookBean book = optionalBook.get();
        // 檢查庫存
        if (book.getStock() < quantity) {
            response.put("message", "庫存不足");
            return response;
        }
        // 查詢購物車項目是否已存在該書籍
        Optional<CartItemBean> existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getBookId().equals(bookId))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItemBean item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setSubtotal(item.getPrice() * item.getQuantity());
            cart.setQuantity(cart.getQuantity() + quantity);
        } else {
            CartItemBean item = new CartItemBean();
            item.setBook(book);
            item.setQuantity(quantity);
            item.setPrice(book.getPrice());
            item.setSubtotal(book.getPrice() * quantity);
            item.setCustomer(cart.getCustomer());
            if (cart.getCustomer() == null) {
                response.put("message", "購物車未關聯顧客");
                return response;
            }
            cart.addItem(item);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        // 設置購物車狀態為「保留」
        StatusBean reservedStatus = statusRepository.findByDetailedStatus("保留");
        cart.setStatus(reservedStatus);
        // 儲存購物車
        cartRepository.save(cart);
        // 回傳 cartId 和訊息
        response.put("message", "商品已加入購物車");
        response.put("cartId", cart.getCartId());
        return response;
    }
  //原版:
//    public String addToCart(Long customerId, Integer bookId, Integer quantity) {
//        // 查詢顧客的購物車
//        CartBean cart = cartRepository.findByCustomerId(customerId);
//        if (cart == null) {
//            cart = new CartBean();
//            cart.setCustomerId(customerId);
//            cart.setCreationTime(LocalDateTime.now());
//            cart.setLastUpdatedTime(LocalDateTime.now());
//            cart.setQuantity(0);  // 初始化數量
//            cart.setItems(new ArrayList<>());  // 確保 items 初始化為空列表
//            // 查詢顧客資訊並設置
//            Optional<CustomerBean> customer = customerRepository.findById(customerId);
//            if (customer.isPresent()) {
//                cart.setCustomer(customer.get());  // 設置顧客
//            } else {
//                return "顧客資料不存在";
//            }
//        } else {
//            cart.setLastUpdatedTime(LocalDateTime.now());
//        }
//        // 查詢書籍
//        Optional<BookBean> optionalBook = bookRepository.findByBookId(bookId);
//        if (optionalBook.isEmpty()) {
//            return "書籍不存在";
//        }
//        BookBean book = optionalBook.get();
//        // 檢查庫存
//        if (book.getStock() < quantity) {
//            return "庫存不足";
//        }
//        // 查詢購物車項目是否已存在該書籍
//        Optional<CartItemBean> existingItem = cart.getItems().stream()
//                .filter(item -> item.getBook().getBookId().equals(bookId))
//                .findFirst();
//        if (existingItem.isPresent()) {
//            // 如果該書籍已存在，更新數量
//            CartItemBean item = existingItem.get();
//            item.setQuantity(item.getQuantity() + quantity); // 增加或減少數量
//            item.setSubtotal(item.getPrice() * item.getQuantity()); // 更新小計
//            cart.setQuantity(cart.getQuantity() + quantity); // 更新總數量
//        } else {
//            // 如果該書籍不存在，創建新的購物車項目
//            CartItemBean item = new CartItemBean();
//            item.setBook(book);
//            item.setQuantity(quantity);
//            item.setPrice(book.getPrice());
//            item.setSubtotal(book.getPrice() * quantity); // 設定小計
//            item.setCustomer(cart.getCustomer()); // 設定客戶
//            if (cart.getCustomer() == null) {
//                return "購物車未關聯顧客";
//            }
//            cart.addItem(item);  // 同時加入購物車與購物車項目
//            cart.setQuantity(cart.getQuantity() + quantity);  // 更新總數量
//        }
//        // 設置購物車狀態為「保留」
//        StatusBean reservedStatus = statusRepository.findByDetailedStatus("保留");
//        cart.setStatus(reservedStatus);
//        // 儲存購物車
//        cartRepository.save(cart);
//        return "商品已加入購物車";
//    }

    // 3. 修改購物車中書籍數量
    public String updateCartItem(Long customerId, Integer bookId, Integer quantity) {
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            return "購物車不存在";
        }
        Optional<BookBean> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalBook.isEmpty()) {
            return "書籍不存在";
        }
        BookBean book = optionalBook.get();
        // 檢查庫存
        if (book.getStock() < quantity) {
            return "庫存不足";
        }
        // 查詢該書籍是否存在於購物車
        Optional<CartItemBean> existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getBookId().equals(bookId))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItemBean item = existingItem.get();
            // 更新數量
            int quantityDifference = quantity - item.getQuantity();  // 計算數量差異
            item.setQuantity(quantity);  // 設定新的數量
            item.setSubtotal(item.getPrice() * quantity); // 更新小計
            // 更新購物車總數量
            cart.setQuantity(cart.getQuantity() + quantityDifference);
            cart.setLastUpdatedTime(LocalDateTime.now());
            // 設置購物車狀態為「保留」
            StatusBean reservedStatus = statusRepository.findByDetailedStatus("保留");
            cart.setStatus(reservedStatus);
            // 儲存購物車
            cartRepository.save(cart);
            return "購物車商品數量已更新";
        } else {
            return "購物車中沒有這本書";
        }
    }

    // 4. 刪除購物車中一項書籍資料
    public String removeCartItem(Long customerId, Integer bookId) {
        // 查詢顧客的購物車
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            return "購物車不存在";
        }
        // 查詢書籍
        Optional<BookBean> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalBook.isEmpty()) {
            return "書籍不存在";
        }
        // 查詢購物車項目
        Optional<CartItemBean> itemToRemove = cart.getItems().stream()
                .filter(item -> item.getBook().getBookId().equals(bookId))
                .findFirst();
        // 如果找到了該書籍項目，刪除它
        if (itemToRemove.isPresent()) {
            CartItemBean item = itemToRemove.get();
            cart.getItems().remove(item);  // 從購物車中移除該項目
            cart.setQuantity(cart.getQuantity() - item.getQuantity());  // 減少總數量
            cartItemRepository.delete(item);  // 真正從資料庫刪除
            // 更新購物車資料
            cart.setLastUpdatedTime(LocalDateTime.now());  // 更新時間
            // 設置購物車狀態為「保留」
            StatusBean reservedStatus = statusRepository.findByDetailedStatus("保留");
            cart.setStatus(reservedStatus);
            cartRepository.save(cart);  // 儲存更新後的購物車
            return "書籍已從購物車中移除";
        } else {
            return "購物車中沒有此書籍";
        }
    }

    // 5. 清空整個購物車
    public String clearCart(Long customerId) {
        // 查詢顧客的購物車
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            return "購物車不存在";
        }
        // 清空購物車中的所有書籍
        cart.getItems().clear();  // 清空購物車中的所有項目
        cart.setQuantity(0);  // 設定購物車數量為0
        // 更新購物車資料
        cart.setLastUpdatedTime(LocalDateTime.now());  // 更新時間
        // 設置購物車狀態為「已清空」
        StatusBean clearedStatus = statusRepository.findByDetailedStatus("已清空");
        cart.setStatus(clearedStatus);
        cartRepository.save(cart);  // 儲存更新後的購物車
        return "購物車已清空";
    }
    
    // 查詢顧客的完整購物車
    public CartBean getCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }
}
