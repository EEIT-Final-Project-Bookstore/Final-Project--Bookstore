package com.finalproject.ispan.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.BookBean;
import com.finalproject.ispan.domain.CustomerBean;
import com.finalproject.ispan.domain.ReviewBean;
import com.finalproject.ispan.repository.BookRepository;
import com.finalproject.ispan.repository.CustomerRepository;
import com.finalproject.ispan.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // Retrieve all reviews
    public List<ReviewBean> getAllReviews() {
        List<ReviewBean> reviews = reviewRepository.findAll();
        reviews.forEach(review -> {
            if (review.getBook() != null) {
                review.getBook().getBookName(); // 显式加载Book对象
            }
        });
        return reviews;
    }

    // Retrieve reviews by Customer ID
    public List<ReviewBean> getReviewsByCustomerID(int customerID) {
        List<ReviewBean> reviews = reviewRepository.findByCustomer_CustomerID(customerID);
        reviews.forEach(review -> {
            // 强制加载 book
            if (review.getBook() != null) {
                review.getBook().getBookId(); // 触发 book 加载
            }
        });
        return reviews;
    }

    // Retrieve a single review by Review ID
    public Optional<ReviewBean> getReviewById(Integer reviewID) {
        return reviewRepository.findById(reviewID);
    }

    public ReviewBean addReview(ReviewBean review) {
        if (review.getBook() == null || review.getBook().getBookId() == null) {
            throw new IllegalArgumentException("Book must not be null and must contain a valid ID.");
        }

        if (review.getCustomer() == null || review.getCustomer().getCustomerID() == null) {
            throw new IllegalArgumentException("Customer must not be null and must contain a valid ID.");
        }

        // 验证 Book 是否存在
        BookBean book = bookRepository.findById(review.getBook().getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + review.getBook().getBookId()));

        // 验证 Customer 是否存在
        CustomerBean customer = customerRepository.findById(review.getCustomer().getCustomerID())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + review.getCustomer().getCustomerID()));

        // 设置持久化对象
        review.setBook(book);
        review.setCustomer(customer);
        review.setReviewTime(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // Update an existing review
    public ReviewBean updateReview(Integer reviewID, ReviewBean reviewDetails) {
        ReviewBean review = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new RuntimeException("ReviewBean not found with id: " + reviewID));

        review.setRating(reviewDetails.getRating());
        review.setContent(reviewDetails.getContent());
        review.setReviewTime(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    // Delete a review by ID
    public void deleteReview(Integer reviewID) {
        reviewRepository.deleteById(reviewID);
    }
}