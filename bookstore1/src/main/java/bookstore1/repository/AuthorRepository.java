package bookstore1.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore1.domain.AuthorBean;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorBean, Integer> {
    // 可自訂查詢方法，例如依作者名稱查詢
    List<AuthorBean> findByAuthorNameContaining(String authorName);
}
