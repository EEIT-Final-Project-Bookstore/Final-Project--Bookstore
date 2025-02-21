package com.finalproject.ispan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.domain.AuthorBean;
import com.finalproject.ispan.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    // 查詢所有作者
    @GetMapping
    public List<AuthorBean> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // 依 ID 查詢作者
    @GetMapping("/{id}")
    public ResponseEntity<AuthorBean> getAuthorById(@PathVariable Integer id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    // 新增作者
    @PostMapping
    public ResponseEntity<AuthorBean> createAuthor(@RequestBody AuthorBean author) {
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    // 更新作者
    @PutMapping("/{id}")
    public ResponseEntity<AuthorBean> updateAuthor(@PathVariable Integer id, @RequestBody AuthorBean author) {
        return ResponseEntity.ok(authorService.updateAuthor(id, author));
    }

    // 刪除作者
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    // 搜尋作者
    @GetMapping("/search")
    public List<AuthorBean> searchAuthors(@RequestParam String keyword) {
        return authorService.searchAuthors(keyword);
    }
}
