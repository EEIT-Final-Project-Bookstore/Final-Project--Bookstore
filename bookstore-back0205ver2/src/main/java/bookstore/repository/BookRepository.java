package bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.BookBean;

@Repository
public interface BookRepository extends JpaRepository<BookBean, Integer> {
    // 可根據需求自訂查詢方法，例如依書名查詢
    List<BookBean> findByBookNameContaining(String bookName);

    // 模糊查詢
    List<BookBean> findByBookNameContainingIgnoreCase(String keyword);

    Optional<BookBean> findByBookId(Integer bookId);
    
 // 透過作者名稱查詢書籍（模糊查詢）
 	List<BookBean> findByAuthor_AuthorNameContainingIgnoreCase(String authorName);

 	 // 透過出版社名稱查詢書籍（模糊查詢）
     List<BookBean> findByPublisher_PublisherNameContainingIgnoreCase(String publisherName);

     
     // 透過子分類名稱查詢書籍（模糊查詢）
     List<BookBean> findBySubcategory_SubcategoryNameContainingIgnoreCase(String subcategoryName);
     
  // 透過類別名稱查詢書籍（模糊查詢）
     List<BookBean> findBySubcategory_Category_CategoryNameContainingIgnoreCase(String categoryName);
}