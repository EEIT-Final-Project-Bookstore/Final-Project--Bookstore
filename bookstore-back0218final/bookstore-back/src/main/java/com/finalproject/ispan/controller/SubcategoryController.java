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

import com.finalproject.ispan.dto.SubcategoryDTO;
import com.finalproject.ispan.service.SubcategoryService;

@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;

    // ✅ 取得所有子分類
    @GetMapping
    public List<SubcategoryDTO> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    // ✅ 根據 ID 取得單一子分類
    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(subcategoryService.getSubcategoryById(id));
    }

    // ✅ 根據分類 ID 取得該分類的所有子分類
    @GetMapping("/category/{categoryId}")
    public List<SubcategoryDTO> getSubcategoriesByCategoryId(@PathVariable Integer categoryId) {
        return subcategoryService.getSubcategoriesByCategoryId(categoryId);
    }

    // ✅ 新增子分類
    @PostMapping
    public ResponseEntity<SubcategoryDTO> createSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {
        return ResponseEntity.ok(subcategoryService.createSubcategory(subcategoryDTO));
    }

    // ✅ 更新子分類
    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> updateSubcategory(@PathVariable Integer id, @RequestBody SubcategoryDTO subcategoryDTO) {
        return ResponseEntity.ok(subcategoryService.updateSubcategory(id, subcategoryDTO));
    }

    // ✅ 刪除子分類
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Integer id) {
        subcategoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }
}