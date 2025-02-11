package bookstore.dto;

public class CouponRequestDTO {
	private Integer cartId; // 購物車 ID (Integer 型別)
	private String couponCode; // 優惠券碼

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
