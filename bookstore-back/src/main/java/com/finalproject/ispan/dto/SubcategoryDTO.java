package com.finalproject.ispan.dto;

public class SubcategoryDTO {
	private Integer subcategoryId;
    private String subcategoryName;
    private Integer categoryId; // ✅ 只存 categoryId，減少關聯數據

    // ✅ Getters 和 Setters
    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
