package com.example.demo.dto;

public class HomepageImageDTO {
	 private Integer id;
	    private Integer imageID;
	    private String bookName;
	    private String description;
	    private Integer price;
	    
	 // 無參構造方法（必要，Spring 框架需要）
	    public HomepageImageDTO() {
	    }

	    // 帶參構造方法，用於 JPQL 自動映射
	    public HomepageImageDTO(Integer id, Integer imageID, String bookName, String description, Integer price) {
	        this.id = id;
	        this.imageID = imageID;
	        this.bookName = bookName;
	        this.description = description;
	        this.price = price;
	    }
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

	    public String getBookName() {
	        return bookName;
	    }

	    public void setBookName(String bookName) {
	        this.bookName = bookName;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Integer getPrice() {
	        return price;
	    }

	    public void setPrice(Integer price) {
	        this.price = price;
	    }
	}
