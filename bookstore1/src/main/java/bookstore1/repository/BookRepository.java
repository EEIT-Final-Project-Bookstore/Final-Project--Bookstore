package bookstore1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore1.domain.BookBean;

@Repository
public interface BookRepository extends JpaRepository<BookBean, Integer> {
    // 可自訂查詢方法，例如依書名查詢
    List<BookBean> findByBookNameContaining(String bookName);
}
