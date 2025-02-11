package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.CategoryBean;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryBean, Integer> {
    // 可自定義查詢方法，例如根據名稱模糊查詢
    List<CategoryBean> findByCategoryNameContaining(String name);
}
