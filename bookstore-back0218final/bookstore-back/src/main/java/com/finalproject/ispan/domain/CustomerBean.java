package com.finalproject.ispan.domain;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
//    @JsonIgnore
//    private byte[] password;
    private byte[] password = new byte[0]; // 初始化為空的 byte[] 陣列

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
    @JsonIgnore  //避免後台查詢顧客時出現迴圈
    private List<OrderBean> orders;

    @OneToOne(mappedBy = "customer")
    @JsonIgnore  //避免後台查詢顧客時出現迴圈
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

//	public byte[] getPassword() {
//		return password;
//	}

//	public void setPassword(byte[] password) {
//		this.password = password;
//	}
	// 🔹 存入資料庫時，把 String 轉成 byte[]
	public void setPassword(String password) {
		this.password = password.getBytes(StandardCharsets.UTF_8);
	}
//	public void setPassword(String password) {
//	    if (password == null) {
//	        this.password = new byte[0]; // 如果為 null，設為空的 byte[] 陣列
//	    } else {
//	        this.password = password.getBytes(StandardCharsets.UTF_8); // 將 String 轉為 byte[]
//	    }
//	}

	// 🔹 從資料庫取出時，把 byte[] 轉回 String
	public String getPassword() {
		return new String(this.password, StandardCharsets.UTF_8);
	}
//	public String getPassword() {
//	    return byteArrayToBase64(this.password); // 假設 this.password 是 byte[] 類型
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
