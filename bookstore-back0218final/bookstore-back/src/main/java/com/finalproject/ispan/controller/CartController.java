package com.finalproject.ispan.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.finalproject.ispan.domain.CartBean;
import com.finalproject.ispan.dto.CartItemDto;
import com.finalproject.ispan.dto.CartItemRequest;
import com.finalproject.ispan.repository.CartRepository;
import com.finalproject.ispan.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    
    @GetMapping("/view/{customerId}")
    public List<CartItemDto> viewCart(@PathVariable Long customerId) {
        return cartService.getCartItemDtos(customerId);  // 返回轉換後的 DTO
    }
    
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody CartItemRequest cartItemRequest) {
        Map<String, Object> response = cartService.addToCart(
            cartItemRequest.getCustomerId(),
            cartItemRequest.getBookId(),
            cartItemRequest.getQuantity()
        );
        if (response.containsKey("cartId")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    //原版:
//    public String addToCart(@RequestBody CartItemRequest cartItemRequest) {
//        // 使用封裝的 DTO 來執行業務邏輯
//        return cartService.addToCart(cartItemRequest.getCustomerId(), 
//                                     cartItemRequest.getBookId(), 
//                                     cartItemRequest.getQuantity());
//    }
    
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
    public String clearCart(@PathVariable Long customerId) {
        return cartService.clearCart(customerId);
    }
    
//    @GetMapping("/{customerId}")
//    public Integer getCartId(@PathVariable Long customerId) {
//        CartBean cart = cartRepository.findByCustomerId(customerId);
//        if (cart != null) {
//            return cart.getCartId();
//        } else {
//        	cart = new CartBean();
//        	return cart.getCartId();
//        }
//    }
    @GetMapping("/{customerId}") 
    public Integer getCartId(@PathVariable String customerId) {
        try {
            Long id = Long.parseLong(customerId); // 確保是 Long
            CartBean cart = cartRepository.findByCustomerId(id);
            return (cart != null) ? cart.getCartId() : 0; // 避免 NullPointerException
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customerId format");
        }
    }
}
