package com.finalproject.ispan.repository;
 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.ispan.domain.LanguageBean;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageBean, Integer> {
    // 可自訂查詢方法，例如依語言名稱查詢
    List<LanguageBean> findByLanguageContaining(String language);
}
