package com.finalproject.ispan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.dto.BookDTO;
import com.finalproject.ispan.service.BookService;

@RestController
@RequestMapping("/api/search")
public class SearchController {
//	@Autowired
//	private BookRepository bookRepository;
	@Autowired
	private BookService bookService;

	@GetMapping
	   public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String keyword) {
	       List<BookDTO> books = bookService.searchBooks(keyword);
	       return ResponseEntity.ok(books);
	   }

	// 透過作者名稱查詢書籍（模糊搜尋）
	@GetMapping("/by-author-name")
	public List<BookDTO> getBooksByAuthorName(@RequestParam String authorName) {
		return bookService.findBooksByAuthorName(authorName);
	}

	//透過出版社名稱查詢書籍（模糊搜尋）
	@GetMapping("/by-publisher-name")
	public List<BookDTO> getBooksByPublisherName(@RequestParam String publisherName) {
		return bookService.findBooksByPublisherName(publisherName);
	}

	// 透過子分類名稱查詢書籍（模糊搜尋）
	@GetMapping("/by-subcategory-name")
	public List<BookDTO> getBooksBySubcategoryName(@RequestParam String subcategoryName) {
		return bookService.findBooksBySubcategoryName(subcategoryName);
	}

	//透過類別名稱查詢書籍（模糊搜尋）
	@GetMapping("/by-category-name")
	public List<BookDTO> getBooksByCategoryName(@RequestParam String categoryName) {
		return bookService.findBooksByCategoryName(categoryName);
	}
}