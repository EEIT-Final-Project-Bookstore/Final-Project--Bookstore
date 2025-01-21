package bookstore1.domain;
 

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
