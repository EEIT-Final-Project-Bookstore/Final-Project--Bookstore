package com.finalproject.ispan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.domain.CategoryBean;
import com.finalproject.ispan.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 查詢所有分類
    @GetMapping
    public List<CategoryBean> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // 依 ID 查詢分類
    @GetMapping("/{id}")
    public ResponseEntity<CategoryBean> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // 新增分類
    @PostMapping
    public ResponseEntity<CategoryBean> createCategory(@RequestBody CategoryBean category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    // 更新分類
    @PutMapping("/{id}")
    public ResponseEntity<CategoryBean> updateCategory(@PathVariable Integer id, @RequestBody CategoryBean category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    // 刪除分類
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

