package com.finalproject.ispan.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Coupons")
public class CouponBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CouponID")
    private Integer couponId;

    @Column(name = "CouponCode", unique = true, nullable = false)
    private String couponCode;

    @Column(name = "Discount", nullable = false)
    private Integer discount;

    @Column(name = "StartDate", nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")  // 設定日期格式
    private LocalDateTime startDate;

    @Column(name = "EndDate", nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")  // 設定日期格式
    private LocalDateTime endDate;

    @Column(name = "MinimumAmount", nullable = false)
    private Integer minimumAmount;

    public CouponBean() {
    	
    }
	public CouponBean(Integer couponId) {
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
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
