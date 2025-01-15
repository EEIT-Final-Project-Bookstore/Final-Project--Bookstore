package com.example.demo.domain;

import jakarta.persistence.*;

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
    private BooksBean book; // 關聯到 BooksBean 實體


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

    public BooksBean getBook() {
        return book; // 返回關聯的書籍對象
    }

    public void setBook(BooksBean book) {
        this.book = book;
    }

	public boolean isEmpty() {
		// TODO Auto-generated method stub
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