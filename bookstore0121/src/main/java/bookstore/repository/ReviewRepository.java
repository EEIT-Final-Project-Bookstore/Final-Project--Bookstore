package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.ReviewBean;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewBean, Integer> {

    // 使用關聯對象的屬性進行查詢
    List<ReviewBean> findByCustomer_CustomerID(int customerID); // 指定 CustomerBean 的主鍵字段

    List<ReviewBean> findByBook_BookId(int bookId);
 // 指定 BooksBean 的主鍵字段
}
