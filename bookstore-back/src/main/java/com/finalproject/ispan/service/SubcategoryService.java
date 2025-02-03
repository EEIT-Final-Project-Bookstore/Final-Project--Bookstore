package com.finalproject.ispan.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.SubcategoryBean;
import com.finalproject.ispan.repository.SubcategoryRepository;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // 查詢所有子分類
    public List<SubcategoryBean> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    // 依 ID 查詢子分類
    public SubcategoryBean getSubcategoryById(Integer id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    // 新增子分類
    public SubcategoryBean createSubcategory(SubcategoryBean subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    // 刪除子分類
    public void deleteSubcategory(Integer id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new RuntimeException("Subcategory not found");
        }
        subcategoryRepository.deleteById(id);
    }
}
