package com.finalproject.ispan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.BookBean;

public interface BookRepository extends JpaRepository<BookBean, Integer> {
	Optional<BookBean> findByBookId(Integer bookId);  // 根據書籍ID查詢書籍
	List<BookBean> findByBookNameContaining(String bookName);
}
