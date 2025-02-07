package bookstore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.ReviewBean;
import bookstore.service.ReviewService;



@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * 获取所有评论
     *
     * @return 所有评论列表
     */
    @GetMapping
    public ResponseEntity<List<ReviewBean>> getAllReviews() {
        List<ReviewBean> reviews = reviewService.getAllReviews();
        reviews.forEach(review -> {
            System.out.println("Review ID: " + review.getReviewID());
            System.out.println("Book ID: " + (review.getBook() != null ? review.getBook().getBookId() : "null"));
            System.out.println("Customer ID: " + (review.getCustomer() != null ? review.getCustomer().getCustomerID() : "null"));
        });
        return reviews.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reviews);
    }

    /**
     * 根据评论 ID 获取评论
     *
     * @param id 评论的 ID
     * @return 单个评论详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewBean> getReviewById(@PathVariable Integer id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 根据顾客 ID 获取评论
     *
     * @param customerID 顾客的 ID
     * @return 顾客的所有评论
     */
    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<ReviewBean>> getReviewsByCustomerID(@PathVariable Integer customerID) {
        List<ReviewBean> reviews = reviewService.getReviewsByCustomerID(customerID);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    /**
     * 添加新的评论
     *
     * @param review 评论内容
     * @return 保存的评论
     */
    @PostMapping
    public ResponseEntity<ReviewBean> addReview(@RequestBody ReviewBean review) {
        try {
            System.out.println("Received Review: " + review);
            System.out.println("Book ID: " + (review.getBook() != null ? review.getBook().getBookId() : "null"));
            System.out.println("Customer ID: " + (review.getCustomer() != null ? review.getCustomer().getCustomerID() : "null"));

            ReviewBean savedReview = reviewService.addReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
        } catch (RuntimeException e) {
            e.printStackTrace(); // 打印异常信息
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 更新评论
     *
     * @param id 评论 ID
     * @param reviewDetails 评论更新内容
     * @return 更新后的评论
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReviewBean> updateReview(@PathVariable Integer id, @RequestBody ReviewBean reviewDetails) {
        try {
            ReviewBean updatedReview = reviewService.updateReview(id, reviewDetails);
            return ResponseEntity.ok(updatedReview);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据评论 ID 删除评论
     *
     * @param id 评论 ID
     * @return 删除操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}