package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.BooksBean;
import com.example.demo.repository.BookRepository;

@Service
public class BooksService {

	@Autowired
	private BookRepository bookRepository;

	public List<BooksBean> searchBooks(String keyword) {
		return bookRepository.findByBookNameContainingIgnoreCase(keyword.trim());
	}

	// 新增方法：根據 BookID 查詢 BooksBean
	public BooksBean findBookById(Integer bookId) {
	    return bookRepository.findByBookId(bookId)
	            .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));
	}

}
