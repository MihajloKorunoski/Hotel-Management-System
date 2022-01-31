package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.repository.FeedbackRepository;
import com.dians.hotelmanagement.repository.HotelRepository;
import com.dians.hotelmanagement.repository.UserRepository;
import com.dians.hotelmanagement.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FeedbackServiceImplementation implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    public FeedbackServiceImplementation(FeedbackRepository feedbackRepository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Feedback> listAllFeedbacksForHotel(Long id) {
        return feedbackRepository.findAllByHotelId(id);
    }

    @Override
    public void addFeedbackToHotel(String user, Long hotel, String reviewText, int stars) {
        User fromUser = userRepository.findByEmail(user).get();
        Hotel toHotel = hotelRepository.findById(hotel).get();
        Feedback feedback = new Feedback(reviewText, stars);
        feedback.setHotel(toHotel);
        feedback.setUser(fromUser);
        feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }
}
