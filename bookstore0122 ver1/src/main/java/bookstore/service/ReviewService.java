package bookstore.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.domain.ReviewBean;
import bookstore.repository.ReviewRepository;



@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Retrieve all reviews
    public List<ReviewBean> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Retrieve reviews by Customer ID
    public List<ReviewBean> getReviewsByCustomerID(int customerID) {
        return reviewRepository.findByCustomer_CustomerID(customerID);
    }

    // Retrieve a single review by Review ID
    public Optional<ReviewBean> getReviewById(Integer reviewID) {
        return reviewRepository.findById(reviewID);
    }

    // Add a new review
    public ReviewBean addReview(ReviewBean review) {
        review.setReviewTime(LocalDateTime.now()); // Set the current time for review
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

