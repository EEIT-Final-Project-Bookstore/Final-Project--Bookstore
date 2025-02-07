package bookstore.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Orders")
public class OrderBean {
	@Id
	@Column(name = "OrderID")
	private String orderId; // Integer 改成 String Ex: BK202501311230001, ...002

	@ManyToOne
	@JoinColumn(name = "CustomerId", nullable = false)
	private CustomerBean customer;

	@ManyToOne
	@JoinColumn(name = "CouponId")
	private CouponBean coupon;

	@Column(name = "TotalAmount", nullable = false)
	private Integer totalAmount;

	@Column(name = "FinalAmount")
	private Integer finalAmount; // 最終應付金額 (扣除折扣金額)

	@ManyToOne
	@JoinColumn(name = "PaymentMethodId", nullable = false)
	private PaymentMethodsBean paymentMethod;

	@Column(name = "OrderCreationTime", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime orderCreationTime; // 訂單生成時間

	@Column(name = "LastUpdatedTime", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime lastUpdatedTime; // 訂單最後更新時間

	@Column(name = "InvoiceNumber")
	private String invoiceNumber; // 發票號碼

	@Column(name = "IssuedTime")
	private LocalDateTime issuedTime; // 發票開立時間

	@ManyToOne
	@JoinColumn(name = "StatusId", nullable = false)
	private StatusBean status;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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

	public Integer getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Integer finalAmount) {
		this.finalAmount = finalAmount;
	}

	public LocalDateTime getOrderCreationTime() {
		return orderCreationTime;
	}

	public void setOrderCreationTime(LocalDateTime orderCreationTime) {
		this.orderCreationTime = orderCreationTime;
	}

	public LocalDateTime getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
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

	public CouponBean getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}
}