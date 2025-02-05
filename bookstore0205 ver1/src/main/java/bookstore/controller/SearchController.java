package bookstore.controller;

import java.awt.print.Book;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.BookBean;
import bookstore.repository.BookRepository;
import bookstore.service.BookService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<BookBean>> searchBooks(@RequestParam String keyword) {
        List<BookBean> results = bookRepository.findByBookNameContainingIgnoreCase(keyword.trim());
        return ResponseEntity.ok(results);
    }

    // 新增根據作者查詢書籍的 API 路由
//    @GetMapping("/searchByAuthor")
//    public List<BookBean> searchBooksByAuthor(@RequestParam String author) {
//        return BookService.findBooksByAuthor(author);
//    }
}
