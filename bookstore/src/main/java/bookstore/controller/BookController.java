package bookstore.controller;
import bookstore.domain.BookBean;
import bookstore.repository.BookRepository;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookBean> getAllBooks() {
        return bookService.getAllBooks();
    }

    //已修改 變成亨版本   
//    @GetMapping("/{id}")
//    public ResponseEntity<BookBean> getBookById(@PathVariable Integer id) {
//        Optional<BookBean> book = bookService.getBookById(id); // 可改為透過服務層查詢
//        if (book.isPresent()) {
//            return ResponseEntity.ok(book.get());
//        } else {
//            return ResponseEntity.status(404).body(null); // 可避免多餘訊息，維持一致性
//        }
//    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookBean> getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BookBean createBook(@RequestBody BookBean book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookBean> updateBook(@PathVariable Integer id, @RequestBody BookBean book) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
