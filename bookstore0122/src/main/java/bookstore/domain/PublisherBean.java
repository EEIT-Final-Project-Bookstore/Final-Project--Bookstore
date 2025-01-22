package bookstore.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Publishers")
public class PublisherBean {

    @Id
    @Column(name = "PublisherID")
    private Integer publisherId;

    @Column(name = "PublisherName", nullable = false, length = 200)
    private String publisherName;

    @Column(name = "PublisherDescription", columnDefinition = "TEXT")
    private String publisherDescription;

    @OneToMany(mappedBy = "publisher")
    private List<BookBean> books;

    // Getters 和 Setters
    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherDescription() {
        return publisherDescription;
    }

    public void setPublisherDescription(String publisherDescription) {
        this.publisherDescription = publisherDescription;
    }

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }
}


//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "Publishers")
//public class PublisherBean {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer publisherID;
//
//    @Column(nullable = false, length = 200)
//    private String publisherName;
//
//    @Column(columnDefinition = "NVARCHAR(MAX)")
//    private String publisherDescription;
//
//    // Getters and Setters
//
//    public Integer getPublisherID() {
//        return publisherID;
//    }
//
//    public void setPublisherID(Integer publisherID) {
//        this.publisherID = publisherID;
//    }
//
//    public String getPublisherName() {
//        return publisherName;
//    }
//
//    public void setPublisherName(String publisherName) {
//        this.publisherName = publisherName;
//    }
//
//    public String getPublisherDescription() {
//        return publisherDescription;
//    }
//
//    public void setPublisherDescription(String publisherDescription) {
//        this.publisherDescription = publisherDescription;
//    }
//}
//


 

