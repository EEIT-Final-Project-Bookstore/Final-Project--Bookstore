package com.finalproject.ispan.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @Column(name = "PublisherID", nullable = false)
    private Integer publisherId;

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

    @Column(name = "SubcategoryID", nullable = false)
    private Integer subcategoryId;

    @Column(name = "ImageURL", length = 200)
    private String imageUrl;

    @Column(name = "ImageDescription", length = 200)
    private String imageDescription;

    @ManyToMany(mappedBy = "books")
    private List<CartBean> carts; // 反向多對多關聯
    
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


//我的方法:
//@Entity
//@Table(name = "Books")
//public class BookBean {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "BookID")
//    private Integer bookId;
//
//    @Column(name = "BookName", nullable = false, length = 200)
//    private String bookName;
//
//    // 外鍵關聯到 PublisherBean
//    @ManyToOne
//    @JoinColumn(name = "PublisherID", nullable = false)
//    private PublisherBean publisher;
//
//    @Column(name = "Price", nullable = false)
//    private Integer price;
//
//    @Column(name = "Stock", nullable = false)
//    private Integer stock;
//
//    @Column(name = "Description")
//    private String description;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "ShelfTime", nullable = false)
//    private Date shelfTime;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "LastUpdated", nullable = false)
//    private Date lastUpdated;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "PublishDate", nullable = false)
//    private Date publishDate;
//
//    @ManyToMany(mappedBy = "books")
//    private List<CartBean> carts;
//    
//    // 外鍵關聯到 SubcategoryBean
//    @ManyToOne
//    @JoinColumn(name = "SubcategoryID", nullable = false)
//    private SubcategoryBean subcategory;
//
//    @Column(name = "ImageURL", length = 200)
//    private String imageUrl;
//
//    @Column(name = "ImageDescription", length = 200)
//    private String imageDescription;
//
//    // 多對多關聯到 AuthorBean
//    @ManyToMany
//    @JoinTable(
//        name = "Books_Authors",
//        joinColumns = @JoinColumn(name = "BookID"),  // 當前實體在關聯表中的外鍵
//        inverseJoinColumns = @JoinColumn(name = "AuthorID")  // 目標實體在關聯表中的外鍵
//    )
//    private Set<AuthorBean> authors;
//
//    public Integer getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Integer bookId) {
//        this.bookId = bookId;
//    }
//
//    public String getBookName() {
//        return bookName;
//    }
//
//    public void setBookName(String bookName) {
//        this.bookName = bookName;
//    }
//
//    public PublisherBean getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(PublisherBean publisher) {
//        this.publisher = publisher;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public Integer getStock() {
//        return stock;
//    }
//
//    public void setStock(Integer stock) {
//        this.stock = stock;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Date getShelfTime() {
//        return shelfTime;
//    }
//
//    public void setShelfTime(Date shelfTime) {
//        this.shelfTime = shelfTime;
//    }
//
//    public Date getLastUpdated() {
//        return lastUpdated;
//    }
//
//    public void setLastUpdated(Date lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }
//
//    public Date getPublishDate() {
//        return publishDate;
//    }
//
//    public void setPublishDate(Date publishDate) {
//        this.publishDate = publishDate;
//    }
//
//    public SubcategoryBean getSubcategory() {
//        return subcategory;
//    }
//
//    public void setSubcategory(SubcategoryBean subcategory) {
//        this.subcategory = subcategory;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public String getImageDescription() {
//        return imageDescription;
//    }
//
//    public void setImageDescription(String imageDescription) {
//        this.imageDescription = imageDescription;
//    }
//
//    public Set<AuthorBean> getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(Set<AuthorBean> authors) {
//        this.authors = authors;
//    }
//}
