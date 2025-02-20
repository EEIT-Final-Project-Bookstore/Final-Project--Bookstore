package com.finalproject.ispan.domain;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Authors")
public class AuthorBean {

    @Id
    @Column(name = "AuthorID", nullable = false)
    private Integer authorId; // 不使用自動生成策略

    @Column(name = "AuthorName", nullable = false, length = 100)
    private String authorName;

    @Column(name = "AuthorDescription", columnDefinition = "TEXT")
    private String authorDescription;

    @OneToMany(mappedBy = "author")
    private List<BookBean> books;

    // Getters 和 Setters
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }
}
