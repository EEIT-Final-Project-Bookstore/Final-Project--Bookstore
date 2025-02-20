package com.finalproject.ispan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.CategoryBean;
import com.finalproject.ispan.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // 查詢所有分類
    public List<CategoryBean> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 依 ID 查詢分類
    public CategoryBean getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // 新增分類
    public CategoryBean createCategory(CategoryBean category) {
        return categoryRepository.save(category);
    }

    // 更新分類
    public CategoryBean updateCategory(Integer id, CategoryBean updatedCategory) {
        CategoryBean category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 更新分類名稱
        category.setCategoryName(updatedCategory.getCategoryName());
        return categoryRepository.save(category);
    }

    // 刪除分類
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
