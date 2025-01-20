package com.example.demo.repository;

import com.example.demo.domain.HomepageImagesBean;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomepageImagesRepository extends JpaRepository<HomepageImagesBean, Integer> {

	 // 查詢所有首頁圖片
   List<HomepageImagesBean> findAll();
// List<HomepageImagesBean> findAllByUrlIsNotNull();
    
    // 根據 BookID 查找單筆首頁圖片（返回第一筆匹配的數據）
   HomepageImagesBean findFirstByBook_BookId(Integer bookId);
// HomepageImagesBean findFirstByBook_BookIdAndUrlIsNotNull(Integer bookId);
    

	// 使用 bookId（Integer）刪除記錄
    //void deleteByBook(BooksBean book)
	void deleteByBook_BookId(Integer bookId);

	
}
