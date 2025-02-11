package com.finalproject.ispan.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Customers")
public class CustomerBean {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CustomerID")
//    private Integer customerId;
	private Long customerID;

    @Column(name = "Username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "CustomerName", length = 100)
    private String customerName;

    @Column(name = "Password", nullable = false, length = 50)
    private byte[] password;

    @Column(name = "Email", unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "PhoneNumber", length = 50)
//    private Integer phoneNumber;
    private String phoneNumber;
    
    @Column(name = "MobileNumber", length = 50)
//    private Integer mobileNumber;
    private String mobileNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RegistrationTime", length = 50)
    private LocalDateTime registrationTime;

    @OneToMany(mappedBy = "customer")
    private List<OrderBean> orders;

    @OneToOne(mappedBy = "customer")
    private CartBean cart;

    @PrePersist
	public void prePersist() {
		this.registrationTime = LocalDateTime.now();
	}
    
	public CustomerBean() {
	}

	public CustomerBean(Integer customerId) {
	}

	public String toString() {
		return "CustomerBean [" + customerID + "," + username + "," + customerName + "," + password + "," + email + ","
				+ phoneNumber + "," + registrationTime + "," + mobileNumber + "]";
	}
	
	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}
//	// 🔹 存入資料庫時，把 String 轉成 byte[]
//	public void setPassword(String password) {
//		this.password = password.getBytes(StandardCharsets.UTF_8);
//	}
//
//	// 🔹 從資料庫取出時，把 byte[] 轉回 String
//	public String getPassword() {
//		return new String(this.password, StandardCharsets.UTF_8);
//	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public Integer getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(Integer phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public Integer getMobileNumber() {
//		return mobileNumber;
//	}
//
//	public void setMobileNumber(Integer mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public LocalDateTime getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registrationTime = registrationTime;
	}

	public List<OrderBean> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderBean> orders) {
		this.orders = orders;
	}

	public CartBean getCart() {
		return cart;
	}

	public void setCart(CartBean cart) {
		this.cart = cart;
	}
}
