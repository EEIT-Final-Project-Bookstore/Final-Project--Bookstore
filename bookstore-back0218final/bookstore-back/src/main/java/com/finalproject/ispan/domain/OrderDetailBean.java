package com.finalproject.ispan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderDetails")
public class OrderDetailBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetailId")
    private Integer detailId;

    @ManyToOne
    @JoinColumn(name = "OrderId", nullable = false)
    private OrderBean order;

    @ManyToOne
    @JoinColumn(name = "BookId", nullable = false)
    private BookBean book;

    @Column(name = "Subtotal")
    private Integer subtotal;
    
    @Column(name = "Quantity")
    private Integer quantity;
    
	public OrderDetailBean() {
	}
	public OrderDetailBean(Integer detailId, OrderBean order, BookBean book, Integer subtotal, Integer quantity) {
		this.detailId = detailId;
		this.order = order;
		this.book = book;
		this.subtotal = subtotal;
		this.quantity = quantity;
	}
	public OrderDetailBean(BookBean book, Integer quantity) {
		this.book = book;
		this.quantity = quantity;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public OrderBean getOrder() {
		return order;
	}
	public void setOrder(OrderBean order) {
		this.order = order;
	}
	public BookBean getBook() {
		return book;
	}
	public void setBook(BookBean book) {
		this.book = book;
	}
	public Integer getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
