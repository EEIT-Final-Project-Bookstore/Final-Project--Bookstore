package bookstore.controller;

import bookstore.domain.CategoryBean;
import bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 查詢所有分類
    @GetMapping
    public List<CategoryBean> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // 依 ID 查詢分類
    @GetMapping("/{id}")
    public ResponseEntity<CategoryBean> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // 新增分類
    @PostMapping
    public ResponseEntity<CategoryBean> createCategory(@RequestBody CategoryBean category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    // 更新分類
    @PutMapping("/{id}")
    public ResponseEntity<CategoryBean> updateCategory(@PathVariable Integer id, @RequestBody CategoryBean category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    // 刪除分類
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
