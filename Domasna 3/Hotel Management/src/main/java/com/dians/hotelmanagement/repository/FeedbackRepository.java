package com.dians.hotelmanagement.repository;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByHotelId(Long hotelId);
}
