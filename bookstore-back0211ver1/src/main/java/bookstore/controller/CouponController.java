package bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.CouponBean;
import bookstore.dto.CouponPageRequest;
import bookstore.dto.CouponRequestDTO;
import bookstore.dto.CouponResponseDTO;
import bookstore.repository.CouponRepository;
import bookstore.service.CouponService;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;

    // **前台-顧客:**
    // 1. 驗證和套用優惠券
    @PostMapping("/apply")
    public CouponResponseDTO applyCoupon(@RequestBody CouponRequestDTO request) {
        return couponService.applyCoupon(request.getCartId(), request.getCouponCode());
    }
    
    // 2. 移除已套用的優惠券
    @DeleteMapping("/remove")
    public CouponResponseDTO removeCoupon(@RequestBody CouponRequestDTO request) {
     return couponService.removeCoupon(request.getCartId(), request.getCouponCode());
    }
    
    // 3.查詢所有優惠券
    @GetMapping("/customer/{customerID}")
    public Map<String, List<CouponResponseDTO>> getCustomerCoupons(@PathVariable Long customerID) {
        return couponService.getCustomerCoupons(customerID);
    }
    
    // **後台-管理者:**
    // 1. 新增優惠券並發放給所有顧客(後台)
    @PostMapping("/admin/addAndAssign")
    public String addCouponAndAssignToAllCustomers(@RequestBody CouponBean coupon) {
     return couponService.addCouponAndAssignToAll(coupon);
    }
    
    // 2. 修改優惠券(後台)
    @PutMapping("/admin/update/{couponId}")
    public CouponResponseDTO updateCoupon(@PathVariable Integer couponId, @RequestBody CouponBean coupon) {
     return couponService.updateCoupon(couponId, coupon);
    }
    
    // 3. 刪除優惠券(後台)
    @DeleteMapping("/admin/delete/{couponId}")
    public CouponResponseDTO deleteCoupon(@PathVariable Integer couponId) {
     return couponService.deleteCoupon(couponId);
    }
    
    //4. 查詢優惠券(後台)改成有分頁功能
    @PostMapping("/admin/coupons")
    public Page<CouponBean> getCoupons(@RequestBody CouponPageRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("startDate").descending());
        return couponRepository.findAll(pageable);
    }
}