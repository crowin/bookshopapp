package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findReviewsByBookIdOrderByTimeDesc(Integer bookId);

}
