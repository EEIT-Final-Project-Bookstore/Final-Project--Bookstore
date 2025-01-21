package tw.com.ispan.domain;

import java.time.LocalDateTime;

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
public class OrdersBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID; // 訂單ID

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private CustomerBean customer; // 顧客，與 Customer 表的關聯

    @Column(nullable = false)
    private Integer totalAmount; // 訂單總金額

    @Column(nullable = false)
    private String orderStatus; // 訂單狀態

    @Column(nullable = false)
    private LocalDateTime orderCreationTime; // 訂單生成時間

    @ManyToOne
    @JoinColumn(name = "PaymentMethodID", nullable = false)
    private PaymentMethodsBean paymentMethod; // 支付方式，與 PaymentMethods 表的關聯

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false)
    private StatusBean status; // 狀態，與 Statuses 表的關聯

    @Column
    private String address; // 地址

    @Column
    private String invoiceNumber; // 發票號碼

    @Column
    private LocalDateTime issuedTime; // 開立時間

    @ManyToOne
    @JoinColumn(name = "CouponID")
    private CouponsBean coupon; // 優惠券，與 Coupons 表的關聯
    
    // Getters and Setters

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
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

	public LocalDateTime getOrderCreationTime() {
		return orderCreationTime;
	}

	public void setOrderCreationTime(LocalDateTime orderCreationTime) {
		this.orderCreationTime = orderCreationTime;
	}

	public PaymentMethodsBean getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodsBean paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

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

	public LocalDateTime getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(LocalDateTime issuedTime) {
		this.issuedTime = issuedTime;
	}

	public CouponsBean getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponsBean coupon) {
		this.coupon = coupon;
	}

    
    
}
