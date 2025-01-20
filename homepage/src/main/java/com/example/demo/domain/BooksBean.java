package com.example.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Books")
public class BooksBean {
	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<HomepageImagesBean> homepageImages;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 使用流水號作為主鍵
	private Integer bookId; // 書籍ID，唯一

	@Column(name = "BookName", nullable = false, length = 200)
	private String bookName; // 書名

	@Column(name = "PublisherID", nullable = false)
	private Integer publisherId; // 出版社ID

	@Column(name = "Price", nullable = false)
	private Integer price; // 價格

	@Column(name = "Stock", nullable = false)
	private Integer stock; // 庫存量

	@Column(name = "Description")
	private String description; // 書籍描述

	@Column(name = "ShelfTime", nullable = false)
	private LocalDateTime shelfTime; // 上架時間

	@Column(name = "LastUpdated", nullable = false)
	private LocalDateTime lastUpdated; // 最後更新時間

	@Column(name = "PublishDate", nullable = false)
	private LocalDate publishDate; // 出版日期

	@Column(name = "SubcategoryID", nullable = false)
	private Integer subcategoryId; // 子類別ID

	@Column(name = "ImageURL", length = 200)
	private String imageUrl; // 圖片URL

	@Column(name = "ImageDescription", length = 200)
	private String imageDescription; // 圖片說明

	// 默認構造函數
	public BooksBean() {
	}

//	// 新增帶參數的構造函數
//	public BooksBean(Integer bookId, String bookName, Integer publisherId, Integer price, Integer stock,
//			String description,
//			LocalDateTime shelfTime, LocalDateTime lastUpdated, LocalDate publishDate, Integer subcategoryId,
//			String imageUrl, String imageDescription) {
//		this.bookId = bookId;
//		this.bookName = bookName;
//		this.publisherId = publisherId;
//		this.price = price;
//		this.stock = stock;
//		this.description = description;
//		this.shelfTime = shelfTime;
//		this.lastUpdated = lastUpdated;
//		this.publishDate = publishDate;
//		this.subcategoryId = subcategoryId;
//		this.imageUrl = imageUrl;
//		this.imageDescription = imageDescription;
//	}

	// Getters and Setters
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(LocalDateTime shelfTime) {
		this.shelfTime = shelfTime;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Integer subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
}
