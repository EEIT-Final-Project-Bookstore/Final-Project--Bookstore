package com.finalproject.ispan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.BookBean;

public interface BookRepository extends JpaRepository<BookBean, Integer> {
	Optional<BookBean> findByBookId(Integer bookId); // 根據書籍ID查詢書籍

	List<BookBean> findByBookNameContaining(String bookName);

	// 模糊查詢
	List<BookBean> findByBookNameContainingIgnoreCaseOrAuthor_AuthorNameContainingIgnoreCaseOrPublisher_PublisherNameContainingIgnoreCase(
	           String bookName, String authorName, String publisherName);

	List<BookBean> findBySubcategory_Category_CategoryName(String categoryName);

	// 透過作者名稱查詢書籍（模糊查詢）
	List<BookBean> findByAuthor_AuthorNameContainingIgnoreCase(String authorName);

	// 透過出版社名稱查詢書籍（模糊查詢）
	List<BookBean> findByPublisher_PublisherNameContainingIgnoreCase(String publisherName);

	// 透過子分類名稱查詢書籍（模糊查詢）
	List<BookBean> findBySubcategory_SubcategoryNameContainingIgnoreCase(String subcategoryName);

	// 透過類別名稱查詢書籍（模糊查詢）
	List<BookBean> findBySubcategory_Category_CategoryNameContainingIgnoreCase(String categoryName);
}
