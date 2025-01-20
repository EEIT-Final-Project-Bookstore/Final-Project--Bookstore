package com.example.demo;


import com.example.demo.domain.BooksBean;
import com.example.demo.repository.BookRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SearchControllerTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testDatabaseConnection() {
        // Arrange: 準備測試數據
        BooksBean book = new BooksBean();
        book.setBookId(100);
        book.setBookName("Java Programming");
        book.setPublisherId(101);
        book.setPrice(500);
        book.setStock(10);
        book.setDescription("Learn Java programming from scratch.");
        book.setShelfTime(java.time.LocalDateTime.now());
        book.setLastUpdated(java.time.LocalDateTime.now());
        book.setPublishDate(java.time.LocalDate.of(2023, 1, 1));
        book.setSubcategoryId(201);
        book.setImageUrl("https://example.com/image.jpg");
        book.setImageDescription("Book cover for Java Programming");

        // Act: 保存數據到資料庫
        bookRepository.save(book);

        // Act: 搜尋書籍
        List<BooksBean> books = bookRepository.findByBookNameContainingIgnoreCase("Java");

        // Assert: 驗證結果
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals("Java Programming", books.get(0).getBookName());
    }
    
    @AfterEach
    public void cleanup() {
        bookRepository.deleteAll();
    }

}
