package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.PublisherBean;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherBean, Integer> {
    // 可自訂查詢方法，例如依出版社名稱查詢
    List<PublisherBean> findByPublisherNameContaining(String publisherName);
}
