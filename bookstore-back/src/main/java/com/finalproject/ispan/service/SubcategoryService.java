package com.finalproject.ispan.service;
 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.CategoryBean;
import com.finalproject.ispan.domain.SubcategoryBean;
import com.finalproject.ispan.dto.SubcategoryDTO;
import com.finalproject.ispan.repository.CategoryRepository;
import com.finalproject.ispan.repository.SubcategoryRepository;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository; // ✅ 需要取得分類

    // ✅ 取得所有子分類
    public List<SubcategoryDTO> getAllSubcategories() {
        return subcategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ 根據 Category ID 取得該分類的所有子分類
    public List<SubcategoryDTO> getSubcategoriesByCategoryId(Integer categoryId) {
        return subcategoryRepository.findByCategory_CategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // ✅ 確保這個方法回傳的是 SubcategoryBean
    public SubcategoryBean getSubcategoryEntityById(Integer id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with ID: " + id));
    }

    // ✅ 新增子分類
    public SubcategoryDTO createSubcategory(SubcategoryDTO subcategoryDTO) {
        SubcategoryBean subcategory = new SubcategoryBean();
        subcategory.setSubcategoryId(subcategoryDTO.getSubcategoryId()); // 設定 ID
        subcategory.setSubcategoryName(subcategoryDTO.getSubcategoryName());

        // 🚀 使用 categoryId 取得 CategoryBean
        CategoryBean category = categoryRepository.findById(subcategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subcategory.setCategory(category);

        SubcategoryBean savedSubcategory = subcategoryRepository.save(subcategory);
        return convertToDTO(savedSubcategory);
    }

    // ✅ 更新子分類
    public SubcategoryDTO updateSubcategory(Integer id, SubcategoryDTO subcategoryDTO) {
        SubcategoryBean subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        subcategory.setSubcategoryName(subcategoryDTO.getSubcategoryName());

        // 🚀 使用 categoryId 取得 CategoryBean
        CategoryBean category = categoryRepository.findById(subcategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subcategory.setCategory(category);

        SubcategoryBean updatedSubcategory = subcategoryRepository.save(subcategory);
        return convertToDTO(updatedSubcategory);
    }

    // ✅ 刪除子分類
    public void deleteSubcategory(Integer id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new RuntimeException("Subcategory not found");
        }
        subcategoryRepository.deleteById(id);
    }

    // ✅ 根據 ID 取得單一子分類
    public SubcategoryDTO getSubcategoryById(Integer id) {
        SubcategoryBean subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        return convertToDTO(subcategory);
    }
    

    // ✅ 將 SubcategoryBean 轉換為 SubcategoryDTO
    private SubcategoryDTO convertToDTO(SubcategoryBean subcategory) {
        SubcategoryDTO dto = new SubcategoryDTO();
        dto.setSubcategoryId(subcategory.getSubcategoryId());
        dto.setSubcategoryName(subcategory.getSubcategoryName());
        dto.setCategoryId(subcategory.getCategory().getCategoryId()); // ✅ 只存 categoryId
        return dto;
    }
    
}