package bookstore.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

=======

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3

import bookstore.domain.HomepageImagesBean;

public interface HomepageImagesRepository extends JpaRepository<HomepageImagesBean, Integer> {

<<<<<<< HEAD
    // 查詢所有首頁圖片
    List<HomepageImagesBean> findAll();

    // 根據 BookID 查找單筆首頁圖片（返回第一筆匹配的數據）
    HomepageImagesBean findFirstByBook_BookId(Integer bookId);

    // 使用 bookId 刪除記錄
    void deleteByBook_BookId(Integer bookId);

    /**
     * JPQL 查詢：根據 Book 的庫存量升序排序，返回對應的前 10 筆 HomepageImagesBean
     */
    @Query("SELECT h FROM HomepageImagesBean h JOIN h.book b ORDER BY b.stock ASC")
    List<HomepageImagesBean> findTop10HomepageImagesByBookStockAsc();
    
    
=======
	 // 查詢所有首頁圖片
   List<HomepageImagesBean> findAll();
// List<HomepageImagesBean> findAllByUrlIsNotNull();
    
    // 根據 BookID 查找單筆首頁圖片（返回第一筆匹配的數據）
   HomepageImagesBean findFirstByBook_BookId(Integer bookId);
// HomepageImagesBean findFirstByBook_BookIdAndUrlIsNotNull(Integer bookId);
    

	// 使用 bookId（Integer）刪除記錄
    //void deleteByBook(BooksBean book)
	void deleteByBook_BookId(Integer bookId);

	
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
}
