package com.finalproject.ispan.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categories")
public class CategoryBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private Integer categoryId;

    @Column(name = "CategoryName", nullable = false, length = 100)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<SubcategoryBean> subcategories;

    // Getters 和 Setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<SubcategoryBean> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryBean> subcategories) {
        this.subcategories = subcategories;
    }
}
