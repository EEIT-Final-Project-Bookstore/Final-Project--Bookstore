package com.example.demo.dto;

public class HomepageImageDTO {
    private Integer id;
    private Integer imageID;
    private Integer bookId; // 替代 bookName
    private String url; // 圖片網址

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
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
