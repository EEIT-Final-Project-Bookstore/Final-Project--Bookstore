package bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bookstore.domain.AuthorBean;
import bookstore.service.AuthorService;

import java.util.List;

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

    // 查詢單一作者
    @GetMapping("/{id}")
    public ResponseEntity<AuthorBean> getAuthorById(@PathVariable Integer id) {
    	  AuthorBean author = authorService.getAuthorById(id);
    	    if (author != null) {
    	        return ResponseEntity.ok(author);
    	    } else {
    	        return ResponseEntity.notFound().build();
    	    }
    }

    // 新增作者 (手動指定 ID)
    @PostMapping
    public ResponseEntity<AuthorBean> createAuthor(@RequestBody AuthorBean author) {
        try {
            AuthorBean createdAuthor = authorService.createAuthor(author);
            return ResponseEntity.ok(createdAuthor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 修改作者資訊
    @PutMapping("/{id}")
    public ResponseEntity<AuthorBean> updateAuthor(@PathVariable Integer id, @RequestBody AuthorBean updatedAuthor) {
        try {
            AuthorBean author = authorService.updateAuthor(id, updatedAuthor);
            return ResponseEntity.ok(author);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除作者
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
