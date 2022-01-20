package com.dians.hotelmanagement.web.rest;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.service.FeedbackService;
import com.dians.hotelmanagement.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reviews")
@PreAuthorize("hasRole('ROLE_USER')")
public class ReviewRestController {
    private final FeedbackService feedbackService;
    private final HotelService hotelService;
    public ReviewRestController(FeedbackService feedbackService, HotelService hotelService) {
        this.feedbackService = feedbackService;
        this.hotelService = hotelService;
    }

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
