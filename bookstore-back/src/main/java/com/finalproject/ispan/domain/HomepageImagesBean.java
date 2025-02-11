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
@Table(name = "HomepageImages")
public class HomepageImagesBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 流水號主鍵
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer imageID;

    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId", nullable = false)
    private BookBean book; // 關聯到 BooksBean 實體

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public BookBean getBook() {
        return book; // 返回關聯的書籍對象
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return "HomepageImagesBean{" +
                "id=" + id +
                ", imageID=" + imageID +
                ", book=" + (book != null ? book.getBookId() : "null") +
                '}';
    }

}
