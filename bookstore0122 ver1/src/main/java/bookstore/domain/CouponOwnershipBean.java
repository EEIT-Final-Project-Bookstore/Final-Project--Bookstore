package bookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CouponOwnership")
public class CouponOwnershipBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SerialID")
	private Integer serialId;
	
	@ManyToOne
	@JoinColumn(name = "CustomerId", nullable = false)
	private CustomerBean customer;
	
	@ManyToOne
	@JoinColumn(name = "CouponId", nullable = false)
	private CouponBean coupon;
	
	@ManyToOne
	@JoinColumn(name = "StatusId", nullable = false)
	private StatusBean status;  //已使用、未使用、已過期

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public CustomerBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	public CouponBean getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}

	public StatusBean getStatus() {
		return status;
	}

	public void setStatus(StatusBean status) {
		this.status = status;
	}

}
