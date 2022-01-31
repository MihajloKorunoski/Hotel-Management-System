package com.dians.hotelmanagement.web.rest;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.repository.FeedbackRepository;
import com.dians.hotelmanagement.service.FeedbackService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reviews")
@PreAuthorize("hasRole('ROLE_USER')")
public class ReviewRestController {
    private final FeedbackService feedbackService;
    private final FeedbackRepository feedbackRepository;
    private final HotelService hotelService;

    public ReviewRestController(FeedbackService feedbackService, FeedbackRepository feedbackRepository, HotelService hotelService) {
        this.feedbackService = feedbackService;
        this.feedbackRepository = feedbackRepository;
        this.hotelService = hotelService;
    }

    //Saves the review from the user and returns a list of all reviews in json format
    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Feedback> findAll(@RequestParam Long hotelId,
                                  @RequestParam String reviewText,
                                  @RequestParam Integer stars) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        feedbackService.addFeedbackToHotel(user.getEmail(), hotelId, reviewText, stars);
        return feedbackService.listAllFeedbacksForHotel(hotelId);
    }
}
