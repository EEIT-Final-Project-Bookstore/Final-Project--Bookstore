package com.finalproject.ispan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.ispan.domain.SubcategoryBean;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryBean, Integer> {
    // 可自訂查詢方法，例如依子分類名稱查詢
	List<SubcategoryBean> findBySubcategoryNameContaining(String subcategoryName);
    List<SubcategoryBean> findByCategory_CategoryId(Integer categoryId);
}
