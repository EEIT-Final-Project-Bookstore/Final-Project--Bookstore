package finatest.domain;

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
@Table(name = "Reviews")
public class ReviewBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewID;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = true, columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(nullable = false)
    private LocalDateTime reviewTime;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false) // 与 Customer 表关联
    private CustomerBean customer;

    @ManyToOne
    @JoinColumn(name = "BookID", nullable = false) // 与 Books 表关联
    private BooksBean book;

    // Getters and Setters

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(LocalDateTime reviewTime) {
        this.reviewTime = reviewTime;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public BooksBean getBook() {
        return book;
    }

    public void setBook(BooksBean book) {
        this.book = book;
    }
}

