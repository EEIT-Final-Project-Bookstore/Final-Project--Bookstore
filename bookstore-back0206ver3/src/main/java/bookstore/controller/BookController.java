package bookstore.controller;

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
import org.springframework.web.bind.annotation.RestController;

import bookstore.dto.BookDTO;
import bookstore.service.BookService;

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
    
 // 依分類取得書籍
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@PathVariable String categoryName) {
        List<BookDTO> books = bookService.getBooksByCategory(categoryName);
        return ResponseEntity.ok(books);
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
    
 
