package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.BookBean;

@Repository
public interface BookRepository extends JpaRepository<BookBean, Integer> {
    // 可根據需求自訂查詢方法，例如依書名查詢
    List<BookBean> findByBookNameContaining(String bookName);
}