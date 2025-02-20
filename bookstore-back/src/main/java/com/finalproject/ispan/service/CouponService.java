package com.finalproject.ispan.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finalproject.ispan.domain.CartBean;
import com.finalproject.ispan.domain.CouponBean;
import com.finalproject.ispan.domain.CouponOwnershipBean;
import com.finalproject.ispan.domain.CustomerBean;
import com.finalproject.ispan.domain.StatusBean;
import com.finalproject.ispan.dto.CouponResponseDTO;
import com.finalproject.ispan.repository.CartRepository;
import com.finalproject.ispan.repository.CouponOwnershipRepository;
import com.finalproject.ispan.repository.CouponRepository;
import com.finalproject.ispan.repository.CustomerRepository;
import com.finalproject.ispan.repository.OrderRepository;
import com.finalproject.ispan.repository.StatusRepository;

@Service
@Transactional
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CouponOwnershipRepository couponOwnershipRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    // **前台-顧客:**
    // 1. 套用優惠券
    public CouponResponseDTO applyCoupon(Integer cartId, String couponCode) {
        // 查找購物車
        CartBean cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("購物車不存在"));
     System.out.println("Applying coupon code: " + couponCode);
        // 查找優惠券
        CouponBean coupon = couponRepository.findByCouponCode(couponCode);
        if (coupon == null) {
            return new CouponResponseDTO(false, 0, "優惠券不存在或無效");
        }
        // 檢查優惠券是否過期/開始
        LocalDateTime now = LocalDateTime.now();
        if (coupon.getEndDate().isBefore(now)) {
            return new CouponResponseDTO(false, 0, "優惠券已過期");
        }
        if (coupon.getStartDate().isAfter(now)) {
            return new CouponResponseDTO(false, 0, "優惠券尚未開始");
        }
        // 檢查優惠券是否已被該用戶使用或失效
        CouponOwnershipBean ownership = couponOwnershipRepository
                .findByCustomerAndCoupon(cart.getCustomer(), coupon)
                .orElseThrow(() -> new IllegalArgumentException("該優惠券不可用"));
        if (!"未使用".equals(ownership.getStatus().getDetailedStatus())) {
            return new CouponResponseDTO(false, 0, "優惠券已使用或無效");
        }
        // 驗證優惠券是否符合最低消費金額門檻
        if (cart.getTotalAmount() < coupon.getMinimumAmount()) {
            return new CouponResponseDTO(false, 0, "未達優惠券使用門檻，需滿 $"  + coupon.getMinimumAmount() + " 才能使用該優惠券");
        }
        // 計算折扣金額
        Integer discountAmount = Math.min(coupon.getDiscount(), cart.getTotalAmount());
        // 如果該優惠券的狀態是「未使用」，則需要先確保 StatusBean 存在並已儲存
        StatusBean usedStatus = statusRepository.findByDetailedStatus("已使用");
        if (usedStatus == null) {
            // 如果 StatusBean 並未儲存，就需要先儲存該狀態
            usedStatus = new StatusBean("已使用");
            statusRepository.save(usedStatus);
        }
        // 更新優惠券的使用狀態
        ownership.setStatus(usedStatus);  // 使用已儲存的狀態
        couponOwnershipRepository.save(ownership);	  // 保存變更
        return new CouponResponseDTO(true, discountAmount, "優惠券使用成功");
    }

    // 2. 移除已套用的優惠券
    public CouponResponseDTO removeCoupon(Integer cartId, String couponCode) {
        // 查找購物車
        CartBean cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("購物車不存在"));
        // 查找優惠券
        CouponBean coupon = couponRepository.findByCouponCode(couponCode);
        // 查找該用戶是否已使用該優惠券
        CouponOwnershipBean ownership = couponOwnershipRepository
                .findByCustomerAndCoupon(cart.getCustomer(), coupon)
                .orElseThrow(() -> new IllegalArgumentException("該優惠券未使用"));
        // 檢查優惠券狀態是否為「已使用」
        if (!"已使用".equals(ownership.getStatus().getDetailedStatus())) {
            return new CouponResponseDTO(false, 0, "該優惠券未套用或已移除");
        }
        // 如果是「已使用」狀態，則將其重置為「未使用」
        StatusBean unusedStatus = statusRepository.findByDetailedStatus("未使用");
        if (unusedStatus == null) {
            unusedStatus = new StatusBean("未使用");
            statusRepository.save(unusedStatus);
        }
        // 更新優惠券的使用狀態為「未使用」
        ownership.setStatus(unusedStatus);
        couponOwnershipRepository.save(ownership);  // 保存更新後的 CouponOwnership
        // 返回結果
        return new CouponResponseDTO(true, 0, "優惠券已成功移除");
    }
    
    // 3.查詢所有優惠券(分為未使用、已使用和已過期)
    public Map<String, List<CouponResponseDTO>> getCustomerCoupons(Long customerID) {
        List<CouponOwnershipBean> ownerships = couponOwnershipRepository.findByCustomerCustomerID(customerID);
        // 分類優惠券
        Map<String, List<CouponResponseDTO>> categorizedCoupons = new HashMap<>();
        categorizedCoupons.put("expired", new ArrayList<>());
        categorizedCoupons.put("valid", new ArrayList<>());
        categorizedCoupons.put("used", new ArrayList<>());
        
        // 當前時間
        LocalDateTime currentTime = LocalDateTime.now();
        
        for (CouponOwnershipBean ownership : ownerships) {
            CouponBean coupon = ownership.getCoupon();
            String status = ownership.getStatus().getDetailedStatus(); // 假設狀態存儲在 StatusBean 的 statusName
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            CouponResponseDTO couponDTO = new CouponResponseDTO(
                true, 
                coupon.getDiscount(),
                "Coupon: " + coupon.getCouponCode() + " valid until " + coupon.getEndDate().format(formatter),
                coupon.getMinimumAmount()
            );
            
            // 如果優惠券已過期，將狀態更新為 "已過期"
            if (coupon.getEndDate().isBefore(currentTime)) {
                // 更新優惠券的狀態為 "已過期"
//                ownership.getStatus().setDetailedStatus("已過期");
                categorizedCoupons.get("expired").add(couponDTO);
            } else if ("未使用".equals(status)) {
                categorizedCoupons.get("valid").add(couponDTO);
            } else if ("已使用".equals(status)) {
                categorizedCoupons.get("used").add(couponDTO);
            }
        }
        return categorizedCoupons;
    }

    //舊版查詢所有優惠券(不分狀態):
//    public List<CouponResponseDTO> getCustomerCoupons(Long customerID) {
//        List<CouponOwnershipBean> ownerships = couponOwnershipRepository.findByCustomerCustomerID(customerID);
//        
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        return ownerships.stream().map(ownership -> {
//            CouponBean coupon = ownership.getCoupon();
//            return new CouponResponseDTO(true, coupon.getDiscount(), 
//                "Coupon: " + coupon.getCouponCode() + " valid until " + coupon.getEndDate().format(formatter));
//        }).collect(Collectors.toList());
//    }

    
    // **後台-管理者:**
    // 1. 新增並發放優惠券給所有顧客(後台)
    public String addCouponAndAssignToAll(CouponBean coupon) {
    	// a. 檢查並新增優惠券
    	if (coupon == null || coupon.getCouponCode() == null || coupon.getCouponCode().isEmpty()) {
    		return "新增失敗：優惠券代碼不可為空";
    	}
    	CouponBean existingCoupon = couponRepository.findByCouponCode(coupon.getCouponCode());
    	if (existingCoupon != null) {
    		return "新增失敗：優惠券代碼已存在";
    	}
    	CouponBean savedCoupon = couponRepository.save(coupon);
    	// b. 發放給所有顧客
    	List<CustomerBean> customers = customerRepository.findAll();
    	StatusBean unusedStatus = statusRepository.findByDetailedStatus("未使用");
    	for (CustomerBean customer : customers) {
    		CouponOwnershipBean ownership = new CouponOwnershipBean();
    		ownership.setCustomer(customer);
    		ownership.setCoupon(savedCoupon);
    		ownership.setStatus(unusedStatus);
    		couponOwnershipRepository.save(ownership);
    	}
    	return "優惠券已成功新增並發放給所有顧客";
    }
    
    // 2. 發放"WELCOME50"優惠券給新註冊的顧客(後台)
    @Transactional
    public void assignWelcomeCoupon(CustomerBean customer) {
    	CouponBean welcomeCoupon = couponRepository.findByCouponCode("WELCOME50");

    	 if (welcomeCoupon != null) {
             // 將優惠券與顧客綁定
             CouponOwnershipBean couponOwnership = new CouponOwnershipBean();
             StatusBean unusedStatus = statusRepository.findByDetailedStatus("未使用");
             couponOwnership.setCustomer(customer);
             couponOwnership.setCoupon(welcomeCoupon);
             couponOwnership.setStatus(unusedStatus); // 可以根據需求設定狀態
             couponOwnershipRepository.save(couponOwnership); // 儲存顧客與優惠券的關聯
         } else {
             throw new RuntimeException("優惠券不存在！");
         }
    }
    
    // 3. 修改優惠券(後台)
    public CouponResponseDTO updateCoupon(Integer couponId, CouponBean coupon) {
    	if (coupon == null) {
    		return new CouponResponseDTO(false, 0, "優惠券資料不完整");
    	}
    	// 用 couponId 查找優惠券
    	CouponBean existingCoupon = couponRepository.findById(couponId).orElse(null);
    	if (existingCoupon == null) {
    		return new CouponResponseDTO(false, 0, "該優惠券不存在");
    	}
    	// 更新優惠券屬性
    	existingCoupon.setCouponCode(coupon.getCouponCode());
    	existingCoupon.setDiscount(coupon.getDiscount());
    	existingCoupon.setStartDate(coupon.getStartDate()); // 使用 @JsonFormat 處理的日期
        existingCoupon.setEndDate(coupon.getEndDate()); // 使用 @JsonFormat 處理的日期
    	existingCoupon.setMinimumAmount(coupon.getMinimumAmount());
    	couponRepository.save(existingCoupon);
    	return new CouponResponseDTO(true, 0, "優惠券更新成功");
    }
    
    // 4. 刪除優惠券(後台)
    public CouponResponseDTO deleteCoupon(Integer couponId) {
    	if (couponId == null) {
    		return new CouponResponseDTO(false, 0, "優惠券ID不可為空");
    	}
    	CouponBean coupon = couponRepository.findById(couponId).orElse(null);
    	if (coupon == null) {
    		return new CouponResponseDTO(false, 0, "該優惠券不存在");
    	}
    	// 檢查是否有訂單仍在使用該優惠券
        long orderCount = orderRepository.countOrdersByCouponId(couponId);
        if (orderCount > 0) {
            return new CouponResponseDTO(false, 0, "該優惠券已被訂單使用，無法刪除");
        }
    	// 先刪除與該優惠券相關的 CouponOwnership 資料
        couponOwnershipRepository.deleteByCoupon_CouponId(couponId);
        // 再刪除優惠券
        couponRepository.delete(coupon);
    	return new CouponResponseDTO(true, 0, "優惠券刪除成功");
    }
    
    // 5. 查詢優惠券(後台)
    public List<CouponBean> getAllCoupons() {
    	return couponRepository.findAll();
    }
}