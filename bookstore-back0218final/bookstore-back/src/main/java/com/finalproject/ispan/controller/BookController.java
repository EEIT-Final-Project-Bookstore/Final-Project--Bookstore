package com.finalproject.ispan.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.dto.BookDTO;
import com.finalproject.ispan.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 取得所有書籍
    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    // 依 ID 取得書籍
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // 新增書籍
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    // 更新書籍
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    // 刪除書籍
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/selected-categories")
    public Map<String, List<BookDTO>> getSelectedCategoriesBooks() {
        List<String> selectedCategories = Arrays.asList("親子共讀", "休閒生活", "語言學習", "文學作品");
        Map<String, List<BookDTO>> booksByCategory = new HashMap<>();

        for (String category : selectedCategories) {
            List<BookDTO> books = bookService.getBooksByCategory(category);
            // 只取前 4 本
            booksByCategory.put(category, books.stream().limit(5).collect(Collectors.toList()));
        }

        return booksByCategory;
    }
    
    @GetMapping("/lifestyle-categories")
    public Map<String, List<BookDTO>> getLifestyleCategoriesBooks() {
        List<String> selectedCategories = Arrays.asList("生活風格", "藝術設計", "醫療保健");
        Map<String, List<BookDTO>> booksByCategory = new HashMap<>();

        for (String category : selectedCategories) {
            List<BookDTO> books = bookService.getBooksByCategory(category);
            booksByCategory.put(category, books.stream().limit(5).collect(Collectors.toList()));
        }

        return booksByCategory;
    }
}
