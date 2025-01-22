package customers.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
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
}
