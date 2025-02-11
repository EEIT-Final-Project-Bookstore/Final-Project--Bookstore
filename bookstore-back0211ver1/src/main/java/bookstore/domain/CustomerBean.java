package bookstore.domain;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

=======
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customers")
public class CustomerBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CustomerID")
	private Long customerID;

	@Column(unique = true, name = "Username")
	private String username;

	@Column(name = "CustomerName")
	private String customerName;

	@Column(name = "Password")
	private byte[] password;

	@Column(name = "Email")
	private String email;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "RegistrationTime")
	private LocalDateTime registrationTime;

	@Column(name = "MobileNumber")
	private String mobileNumber;

<<<<<<< HEAD
	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<OrderBean> orders;

	@JsonIgnore
=======
	@OneToMany(mappedBy = "customer")
	private List<OrderBean> orders;

>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
	@OneToOne(mappedBy = "customer")
	private CartBean cart;

	@PrePersist
	public void prePersist() {
		this.registrationTime = LocalDateTime.now();
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	// 🔹 存入資料庫時，把 String 轉成 byte[]
	public void setPassword(String password) {
		this.password = password.getBytes(StandardCharsets.UTF_8);
	}

	// 🔹 從資料庫取出時，把 byte[] 轉回 String
	public String getPassword() {
		return new String(this.password, StandardCharsets.UTF_8);
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

	public LocalDateTime getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registrationTime = registrationTime;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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


