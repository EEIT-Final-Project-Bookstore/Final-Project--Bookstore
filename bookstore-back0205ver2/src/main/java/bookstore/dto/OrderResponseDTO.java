package bookstore.dto;

public class OrderResponseDTO {
	private String orderId; // 訂單 ID，Integer 改成 String
	private Integer finalAmount; // 最終應付金額
	// private String paymentUrl; // 綠界支付連結
	private String message;

	public OrderResponseDTO() {
	}

	// 若有錯誤訊息，構造一個包含錯誤訊息的回應
	public OrderResponseDTO(String message) {
		this.message = message;
	}

	// 若成功創建訂單，則返回包含訂單 ID 和金額
	public OrderResponseDTO(String orderId, Integer finalAmount) {
		this.orderId = orderId;
		this.finalAmount = finalAmount;
	}

	public OrderResponseDTO(String orderId, Integer finalAmount, String message) {
		this.orderId = orderId;
		this.finalAmount = finalAmount;
		// this.paymentUrl = paymentUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Integer finalAmount) {
		this.finalAmount = finalAmount;
	}

	// public String getPaymentUrl() {
	// return paymentUrl;
	// }
	// public void setPaymentUrl(String paymentUrl) {
	// this.paymentUrl = paymentUrl;
	// }
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
