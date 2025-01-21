package com.finalproject.ispan.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finalproject.ispan.domain.BookBean;
import com.finalproject.ispan.domain.CartBean;
import com.finalproject.ispan.domain.CartItemBean;
import com.finalproject.ispan.repository.BookRepository;
import com.finalproject.ispan.repository.CartRepository;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;
    
    // 查詢顧客的購物車內容
    public List<CartItemBean> getCartContents(Integer customerId) {
        // 根據顧客ID查詢購物車
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            return null;  // 如果沒有找到顧客的購物車，返回null
        }
        return cart.getItems();  // 假設CartBean有一個items屬性，儲存所有的CartItem
    }

    // 新增書籍至購物車
    public String addToCart(Integer customerId, Integer bookId, Integer quantity) {
        // 查詢顧客的購物車
        CartBean cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            cart = new CartBean();
            cart.setCustomerId(customerId);
            cart.setCreationTime(LocalDateTime.now());
            cart.setLastUpdatedTime(LocalDateTime.now());
            cart.setQuantity(0);  // 初始化數量
            cart.setItems(new ArrayList<>());  // 確保 items 初始化為空列表
        } else {
            cart.setLastUpdatedTime(LocalDateTime.now());
        }

        // 查詢書籍
        Optional<BookBean> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalBook.isEmpty()) {
            return "書籍不存在";
        }
        BookBean book = optionalBook.get();
        
        // 檢查庫存
        if (book.getStock() < quantity) {
            return "庫存不足";
        }

        // 查詢購物車項目是否已存在該書籍
        Optional<CartItemBean> existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getBookId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            // 如果該書籍已存在，更新數量
            CartItemBean item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity); // 增加或減少數量
            cart.setQuantity(cart.getQuantity() + quantity); // 更新總數量
            cart.setStatusId(4); // 將購物車中的狀態ID設為4 (如果4=保留)
        } else {
            // 如果該書籍不存在，創建新的購物車項目
            CartItemBean item = new CartItemBean();
            item.setBook(book);
            item.setQuantity(quantity);
            cart.addItem(item);  // 同時加入購物車與購物車項目
            cart.setQuantity(cart.getQuantity() + quantity);  // 更新總數量
            cart.setStatusId(4); // 將購物車中的狀態ID設為4 (如果4=保留)
        }

        // 儲存購物車
        cartRepository.save(cart);
        return "商品已加入購物車";
    }

    // 修改購物車中書籍數量
    public String updateCartItem(Integer customerId, Integer bookId, Integer quantity) {
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

            // 更新購物車總數量
            cart.setQuantity(cart.getQuantity() + quantityDifference);
            cart.setLastUpdatedTime(LocalDateTime.now());
            cart.setStatusId(4); // 將購物車中的狀態ID設為4 (如果4=保留)
        } else {
            return "購物車中沒有這本書";
        }

        // 儲存購物車
        cartRepository.save(cart);
        return "購物車商品數量已更新";
    }

    // 刪除購物車中一項書籍資料
    public String removeCartItem(Integer customerId, Integer bookId) {
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
            
            // 更新購物車資料
            cart.setLastUpdatedTime(LocalDateTime.now());  // 更新時間
            cart.setStatusId(4);  // 將購物車中的狀態ID設為4 (如果4=保留)
            cartRepository.save(cart);  // 儲存更新後的購物車
            
            return "書籍已從購物車中移除";
        } else {
            return "購物車中沒有此書籍";
        }
    }
    
    // 清空整個購物車
    public String clearCart(Integer customerId) {
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
        cart.setStatusId(5);  // 將購物車中的狀態ID設為5 (如果5=移除)
        cartRepository.save(cart);  // 儲存更新後的購物車
        return "購物車已清空";
    }
    
    //應用、移除和驗證優惠券
    
}
