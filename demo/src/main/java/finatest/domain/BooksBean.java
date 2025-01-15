package finatest.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class BooksBean {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "BookID") // 映射数据库中的列名
		private Integer bookID;

	    @Column(nullable = false, length = 200)
	    private String bookName;

	    @ManyToOne
	    @JoinColumn(name = "PublisherID", nullable = false)
	    private PublisherBean publisher;

	    @Column(nullable = false)
	    private Integer price;

	    @Column(nullable = false)
	    private Integer stock;

	    @Column(columnDefinition = "NVARCHAR(MAX)")
	    private String description;

	    @Column(nullable = false)
	    private LocalDateTime shelfTime;

	    @Column(nullable = false)
	    private LocalDateTime lastUpdated;

	    @Column(nullable = false)
	    private LocalDate publishDate;

	    @ManyToOne
	    @JoinColumn(name = "SubcategoryID", nullable = false)
	    private SubcategoryBean subcategory;

	    @Column(length = 200)
	    private String imageURL;

	    @Column(length = 200)
	    private String imageDescription;

	    // Getters and Setters

	    public Integer getBookID() {
	        return bookID;
	    }

	    public void setBookID(Integer bookID) {
	        this.bookID = bookID;
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

	    public SubcategoryBean getSubcategory() {
	        return subcategory;
	    }

	    public void setSubcategory(SubcategoryBean subcategory) {
	        this.subcategory = subcategory;
	    }

	    public String getImageURL() {
	        return imageURL;
	    }

	    public void setImageURL(String imageURL) {
	        this.imageURL = imageURL;
	    }

	    public String getImageDescription() {
	        return imageDescription;
	    }

	    public void setImageDescription(String imageDescription) {
	        this.imageDescription = imageDescription;
	    }
	}

