package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.BooksBean;
import com.example.demo.repository.BookRepository;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<BooksBean>> searchBooks(@RequestParam String keyword) {
        List<BooksBean> results = bookRepository.findByBookNameContainingIgnoreCase(keyword.trim());
        return ResponseEntity.ok(results);
    }
}





