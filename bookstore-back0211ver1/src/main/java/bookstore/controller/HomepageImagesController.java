package bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.BookBean;
import bookstore.domain.HomepageImagesBean;
import bookstore.dto.HomepageImageDTO;
import bookstore.service.BookService;
import bookstore.service.HomepageImagesService;

@RestController
@RequestMapping("/api/homepage")
public class HomepageImagesController {

	@Autowired
	private HomepageImagesService homepageImagesService;
	@Autowired
	private BookService bookService;

	// 查詢所有首頁圖片
	@GetMapping("/all")
	public List<HomepageImageDTO> getAllHomepageImages() {
		return homepageImagesService.getAllHomepageImages();
	}
<<<<<<< HEAD
	
	 @GetMapping("/top10")
	    public List<HomepageImagesBean> getTop10BooksByStock() {
	        return homepageImagesService.getTop10BooksByStock();
	    }
=======

>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
	// 根據 BookId 查詢單筆首頁圖片
	@GetMapping("/books/image-by-book")
	public HomepageImageDTO getHomepageImageByBookID(@RequestParam Integer bookId) {
		return homepageImagesService.getHomepageImageByBookID(bookId);
	}

	// // 根據 BookId 查詢單筆首頁圖片
	// @GetMapping("/books/{bookId}/image-by-book")
	// public HomepageImageDTO getHomepageImageByBookID(@PathVariable("bookId")
	// Integer bookId) {
	// return homepageImagesService.getHomepageImageByBookID(bookId);
	// }
	// 根據 BookId 刪除首頁圖片
	@DeleteMapping("/delete-by-book")
	public String deleteHomepageImagesByBookId(@RequestParam Integer bookId) {
		homepageImagesService.deleteHomepageImagesByBookId(bookId);
		return "Homepage images associated with bookId " + bookId + " have been deleted successfully.";
	}

	// 新增首頁圖片
	@PostMapping("/add")
	public ResponseEntity<HomepageImagesBean> addHomepageImages(@RequestBody HomepageImagesBean homepageImages) {
		if (homepageImages.getImageID() == null) {
			throw new IllegalArgumentException("Image ID cannot be null");
		}
		if (homepageImages.getBook() == null || homepageImages.getBook().getBookId() == null) {
			System.out.println("homepageImages is null");
			throw new IllegalArgumentException("Book or Book ID cannot be null");
		}

		System.out.println("HomepageImagesBean: " + homepageImages);
		Integer bookId = homepageImages.getBook().getBookId();
		System.out.println("Received HomepageImage with Book ID: " + bookId);

		// 根據BookID查詢Book實體
		// 使用 bookId 查詢 BooksBean 實體
		BookBean book = bookService.findBookById(bookId);
		homepageImages.setBook(book);

		// 保存數據
		HomepageImagesBean savedImage = homepageImagesService.addHomepageImages(homepageImages);
		return ResponseEntity.ok(savedImage);
	}

	@PutMapping("/update-by-book")
	public ResponseEntity<HomepageImagesBean> updateHomepageImagesBookId(Integer bookId,
			@RequestBody HomepageImagesBean updatedData) {

		System.out.println("Received Update Request for Book ID: " + bookId);
		System.out.println("Updated Data: " + updatedData);

		HomepageImagesBean updatedImage = homepageImagesService.updateHomepageImagesBookId(bookId, updatedData);

		System.out.println("Updated HomepageImagesBean: " + updatedImage);
		return ResponseEntity.ok(updatedImage);
	}
}