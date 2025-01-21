package bookstore1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore1.domain.SubcategoryBean;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryBean, Integer> {
    // 可自訂查詢方法，例如依子分類名稱查詢
    List<SubcategoryBean> findBySubcategoryNameContaining(String subcategoryName);
}
