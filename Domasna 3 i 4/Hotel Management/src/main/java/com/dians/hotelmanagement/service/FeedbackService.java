package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<Feedback> listAllFeedbacksForHotel(Long hotel);

    void addFeedbackToHotel(String user, Long hotel, String reviewText, int stars);

    Optional<Feedback> findById(Long id);
}
