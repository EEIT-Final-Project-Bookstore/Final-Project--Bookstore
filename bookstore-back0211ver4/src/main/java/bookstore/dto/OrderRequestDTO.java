package bookstore.dto;

public class OrderRequestDTO {
	private Integer cartId;
	private String couponCode;

	public OrderRequestDTO() {
	}

	public OrderRequestDTO(Integer cartId) {
		this.cartId = cartId;
	}

	public OrderRequestDTO(Integer cartId, String couponCode) {
		this.cartId = cartId;
		this.couponCode = couponCode;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
}