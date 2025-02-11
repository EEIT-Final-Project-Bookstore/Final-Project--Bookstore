package com.finalproject.ispan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finalproject.ispan.domain.HomepageImagesBean;

public interface HomepageImagesRepository extends JpaRepository<HomepageImagesBean, Integer> {

    // 查詢所有首頁圖片
    List<HomepageImagesBean> findAll();

    // 根據 BookID 查找單筆首頁圖片（返回第一筆匹配的數據）
    HomepageImagesBean findFirstByBook_BookId(Integer bookId);

    // 使用 bookId 刪除記錄
    void deleteByBook_BookId(Integer bookId);

    /**
     * JPQL 查詢：根據 Book 的庫存量升序排序，返回對應的前 10 筆 HomepageImagesBean
     */
    @Query("SELECT h FROM HomepageImagesBean h JOIN h.book b ORDER BY b.stock ASC")
    List<HomepageImagesBean> findTop10HomepageImagesByBookStockAsc();
    
    
}
