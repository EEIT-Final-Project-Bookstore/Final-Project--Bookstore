package com.finalproject.ispan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Orders")
public class OrderBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderID")
	private Integer orderId;

	@ManyToOne
	@JoinColumn(name = "CustomerId", nullable = false)
	private CustomerBean customer;

	@Column(name = "TotalAmount", nullable = false)
	private Integer totalAmount;

	@Column(name = "OrderStatus", nullable = false, length = 50)
	private String orderStatus;

	@Column(name = "OrderCreationTime", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private java.util.Date orderCreationTime;

	// @ManyToOne
	// @JoinColumn(name = "PaymentMethodId", nullable = false)
	// private PaymentMethodBean paymentMethod;

	@ManyToOne
	@JoinColumn(name = "StatusId", nullable = false)
	private StatusBean status;

	@Column(name = "Address")
	private String address;

	@Column(name = "InvoiceNumber")
	private String invoiceNumber;

	@Column(name = "IssuedTime")
	private java.util.Date issuedTime;

	@ManyToOne
	@JoinColumn(name = "CouponId")
	private CouponBean coupon;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public CustomerBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public java.util.Date getOrderCreationTime() {
		return orderCreationTime;
	}

	public void setOrderCreationTime(java.util.Date orderCreationTime) {
		this.orderCreationTime = orderCreationTime;
	}

	// public PaymentMethodBean getPaymentMethod() {
	// return paymentMethod;
	// }

	// public void setPaymentMethod(PaymentMethodBean paymentMethod) {
	// this.paymentMethod = paymentMethod;
	// }

	public StatusBean getStatus() {
		return status;
	}

	public void setStatus(StatusBean status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public java.util.Date getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(java.util.Date issuedTime) {
		this.issuedTime = issuedTime;
	}

	public CouponBean getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}

}
