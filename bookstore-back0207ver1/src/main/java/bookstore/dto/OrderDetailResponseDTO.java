package bookstore.dto;

import java.util.List;

public class OrderDetailResponseDTO {
	private String orderId;
	private String detailedStatus;
	private String orderCreationTime;
	private Integer finalAmount;
	private String invoiceNumber;
	private List<CartItemDto> items;

	public OrderDetailResponseDTO() {
	}

	public OrderDetailResponseDTO(String orderId, String detailedStatus, String orderCreationTime,
			Integer finalAmount, String invoiceNumber, List<CartItemDto> items) {
		this.orderId = orderId;
		this.detailedStatus = detailedStatus;
		this.orderCreationTime = orderCreationTime;
		this.finalAmount = finalAmount;
		this.invoiceNumber = invoiceNumber;
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDetailedStatus() {
		return detailedStatus;
	}

	public void setDetailedStatus(String detailedStatus) {
		this.detailedStatus = detailedStatus;
	}

	public String getOrderCreationTime() {
		return orderCreationTime;
	}

	public void setOrderCreationTime(String orderCreationTime) {
		this.orderCreationTime = orderCreationTime;
	}

	public Integer getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Integer finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public List<CartItemDto> getItems() {
		return items;
	}

	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}
}
