package tw.com.ispan.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Coupons")
public class CouponsBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CouponID")
    private Integer couponID; // 優惠券ID

    @Column(name = "CouponCode", nullable = false, length = 50)
    private String couponCode; // 優惠碼

    @Column(name = "Discount", nullable = false)
    private Float discount; // 折扣 (% 或 -$)

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate; // 開始日期

    @Column(name = "EndDate", nullable = false)
    private LocalDateTime endDate; // 結束日期

    @Column(name = "MinimumAmount", nullable = false)
    private Integer minimumAmount; // 適用的最低消費金額

    
    // Getters and Setters
    public Integer getCouponID() {
        return couponID;
    }

    public void setCouponID(Integer couponID) {
        this.couponID = couponID;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Integer minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    
}
