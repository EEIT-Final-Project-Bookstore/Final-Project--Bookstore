package customers.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Wishlists")
public class WishlistBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WishlistID")
	private Integer wishlistID;

	@Column(name = "CustomerID")
	private Integer customerID;

	public String toString() {
		return "WishlistBean[" + wishlistID + "," + customerID + "]";
	}

	public Integer getWishlistID() {
		return wishlistID;
	}

	public void setWishlistID(Integer wishlistID) {
		this.wishlistID = wishlistID;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

}
