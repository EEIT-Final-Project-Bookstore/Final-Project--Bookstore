package bookstore.dto;

public class CouponResponseDTO {
	private boolean valid; // 是否成功
	private Integer discountAmount; // 折扣金額
	private String message; // 成功/失敗訊息

	public CouponResponseDTO(boolean valid, Integer discountAmount, String message) {
		this.valid = valid;
		this.discountAmount = discountAmount;
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Integer getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
