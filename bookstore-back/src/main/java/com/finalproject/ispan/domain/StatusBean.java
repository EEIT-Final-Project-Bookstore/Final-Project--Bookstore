package com.finalproject.ispan.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Statuses")
public class StatusBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private Integer statusId;

    @Column(name="DetailedStatus", length = 50, nullable = false)
    private String detailedStatus;

//    @OneToMany(mappedBy = "status")
//    private List<CartBean> carts;
    
    @OneToMany(mappedBy = "status")
    private List<OrderBean> orders;
    
	@OneToMany(mappedBy = "status")
	private List<CouponOwnershipBean> couponOwnerships;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getDetailedStatus() {
		return detailedStatus;
	}

	public void setDetailedStatus(String detailedStatus) {
		this.detailedStatus = detailedStatus;
	}

	public List<OrderBean> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderBean> orders) {
		this.orders = orders;
	}
    
}
