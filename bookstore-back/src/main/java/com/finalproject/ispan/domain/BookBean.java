package com.finalproject.ispan.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
// @JsonIgnore
	@JsonIgnoreProperties("books") // 防止 JSON 迴圈
	@JoinColumn(name = "PublisherID", nullable = false)
	private PublisherBean publisher;

	@ManyToOne
// @JsonIgnore
	@JsonIgnoreProperties("books") // 防止 JSON 迴圈
	@JoinColumn(name = "AuthorID", nullable = false)
	private AuthorBean author;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "LanguageID", nullable = false)
	private LanguageBean language;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "SubcategoryID", nullable = false)
	private SubcategoryBean subcategory;

	@Column(name = "Price", nullable = false)
	private Integer price;

	@Column(name = "Stock", nullable = false)
	private Integer stock;

	@Column(name = "Description", columnDefinition = "TEXT")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ShelfTime", nullable = false)
	private Date shelfTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastUpdated", nullable = false)
	private Date lastUpdated;

	// ✅ **合併 @PrePersist 方法**
	@PrePersist
	protected void onCreate() {
		Date now = new Date();
		if (this.shelfTime == null) {
			this.shelfTime = now; // 🔹 shelfTime 預設當前時間
		}
		if (this.lastUpdated == null) {
			this.lastUpdated = now; // 🔹 lastUpdated 也設置時間
		}
	}

	// ✅ **確保 lastUpdated 在更新時更新**
	@PreUpdate
	protected void onUpdate() {
		this.lastUpdated = new Date();
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PublishDate", nullable = false)
	private Date publishDate;

	@Column(name = "ImageURL", length = 200)
	private String imageUrl;

	@Column(name = "ImageDescription", length = 200)
	private String imageDescription;

	@ManyToMany(mappedBy = "books")
	private List<CartBean> carts; // 反向多對多關聯
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({ "book", "publisher", "author", "language", "subcategory" })
	private List<ReviewBean> reviews;

	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<HomepageImagesBean> homepageImages;
	// One-to-Many 關聯到 HomepageImagesBean

	// Getter 和 Setter
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