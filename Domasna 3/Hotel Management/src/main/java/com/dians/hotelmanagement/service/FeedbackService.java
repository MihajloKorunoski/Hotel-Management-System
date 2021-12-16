package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    public List<Feedback> listAllFeedbacksForHotel(Long hotel);
    public void addFeedbackToHotel(String user, Long hotel, String reviewText, int stars);
    public void deleteFeedbackFromHotel(Long feedbackId, String user);
    public Optional<Feedback> findById(Long id);

}
