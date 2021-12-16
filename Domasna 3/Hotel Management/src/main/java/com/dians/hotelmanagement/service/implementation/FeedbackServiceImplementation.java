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
    public List<Feedback> listAllFeedbacksForHotel(Long hotel) {
        Optional<Hotel> h=hotelRepository.findById(hotel);
        if(h.isEmpty())
            return new ArrayList<>();
        return feedbackRepository.findAllByHotel(h.get());
    }

    @Override
    public void addFeedbackToHotel(String user, Long hotel, String reviewText, int stars) {
        User userFromDb=userRepository.getById(user);
        Hotel hotelFromDb=hotelRepository.getById(hotel);
        feedbackRepository.save(new Feedback(userFromDb,hotelFromDb,reviewText,stars));
    }

    @Override
    public void deleteFeedbackFromHotel(Long feedbackId, String user) {
        Optional<Feedback> f=feedbackRepository.findById(feedbackId);
        Optional<User> u=userRepository.findById(user);
        if(f.isEmpty())
            return;
        if(f.get().getUser().getEmail().equals(u.get().getEmail()))
            feedbackRepository.deleteByFeedbackId(feedbackId);
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }
}
