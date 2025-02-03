package com.finalproject.ispan.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Books")
public class BookBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookID")
    private Integer bookId;

    @Column(name = "BookName", nullable = false, length = 200)
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "PublisherID", nullable = false)
    @JsonIgnore // 忽略序列化 books，避免輪迴
    private PublisherBean publisher;

    @ManyToOne
    @JoinColumn(name = "AuthorID", nullable = false)
    @JsonIgnore
    private AuthorBean author;

    @ManyToOne
    @JoinColumn(name = "LanguageID", nullable = false)
    @JsonIgnore
    private LanguageBean language;

    @ManyToOne
    @JoinColumn(name = "SubcategoryID", nullable = false)
    @JsonIgnore
    private SubcategoryBean subcategory;

    @Column(name = "Price", nullable = false)
    private Integer price;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Column(name = "Description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ShelfTime", nullable = false)
    private Date shelfTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdated", nullable = false)
    private Date lastUpdated;

    @Temporal(TemporalType.DATE)
    @Column(name = "PublishDate", nullable = false)
    private Date publishDate;

    @Column(name = "ImageURL", length = 200)
    private String imageUrl;

    @Column(name = "ImageDescription", length = 200)
    private String imageDescription;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore // 忽略序列化 carts，避免輪迴
    private List<CartBean> carts; // 反向多對多關聯

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

	public PublisherBean getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherBean publisher) {
		this.publisher = publisher;
	}

	public AuthorBean getAuthor() {
		return author;
	}

	public void setAuthor(AuthorBean author) {
		this.author = author;
	}

	public LanguageBean getLanguage() {
		return language;
	}

	public void setLanguage(LanguageBean language) {
		this.language = language;
	}

	public SubcategoryBean getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryBean subcategory) {
		this.subcategory = subcategory;
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

	public Date getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(Date shelfTime) {
		this.shelfTime = shelfTime;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	public List<CartBean> getCarts() {
		return carts;
	}

	public void setCarts(List<CartBean> carts) {
		this.carts = carts;
	}
}