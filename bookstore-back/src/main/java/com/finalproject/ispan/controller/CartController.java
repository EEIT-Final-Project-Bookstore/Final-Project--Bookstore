package com.finalproject.ispan.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.dto.CartItemDto;
import com.finalproject.ispan.dto.CartItemRequest;
import com.finalproject.ispan.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    
    //購物車相關:
    // 使用 POST 方法查詢顧客的購物車內容
    @GetMapping("/view/{customerId}")
    public List<CartItemDto> viewCart(@PathVariable Integer customerId) {
        return cartService.getCartItemDtos(customerId);  // 返回轉換後的 DTO
    }
    
    @PostMapping("/add")
    public String addToCart(@RequestBody CartItemRequest cartItemRequest) {
        // 使用封裝的 DTO 來執行業務邏輯
        return cartService.addToCart(cartItemRequest.getCustomerId(), 
                                     cartItemRequest.getBookId(), 
                                     cartItemRequest.getQuantity());
    }
    
    @PutMapping("/update")
    public String updateCartItem(@RequestBody CartItemRequest cartItemRequest) {
        return cartService.updateCartItem(cartItemRequest.getCustomerId(), 
                                          cartItemRequest.getBookId(), 
                                          cartItemRequest.getQuantity());
    }

    @DeleteMapping("/delete")
    public String removeCartItem(@RequestBody CartItemRequest cartItemRequest) {
        return cartService.removeCartItem(cartItemRequest.getCustomerId(), 
                                          cartItemRequest.getBookId());
    }
    
    @DeleteMapping("/clear/{customerId}")
    public String clearCart(@PathVariable Integer customerId) {
        return cartService.clearCart(customerId);
    }
}
