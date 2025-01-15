package finatest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import finatest.domain.BooksBean;
import finatest.domain.CustomerBean;
import finatest.domain.ReviewBean;
import finatest.respository.BooksRepository;
import finatest.respository.CustomerRepository;
import finatest.respository.ReviewRepository;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testInsertReview() {
        // 获取已有书籍
        BooksBean book = booksRepository.findById(1).orElse(null); // 假设 BookID=1 存在
        assertNotNull(book, "书籍不存在");

        // 获取已有顾客
        CustomerBean customer = customerRepository.findById(1).orElse(null); // 假设 CustomerID=1 存在
        assertNotNull(customer, "顾客不存在");

        // 创建并保存评论
        ReviewBean review = new ReviewBean();
        review.setBook(book);
        review.setCustomer(customer);
        review.setRating(5);
        review.setContent("This is a great book!");
        review.setReviewTime(LocalDateTime.now());

        ReviewBean savedReview = reviewRepository.save(review);
        assertNotNull(savedReview);
        assertNotNull(savedReview.getReviewID());

        System.out.println("Inserted Review ID: " + savedReview.getReviewID());
    }
}