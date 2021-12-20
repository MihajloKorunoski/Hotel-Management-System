//package com.dians.hotelmanagement.web.controller;
//
//import com.dians.hotelmanagement.model.Feedback;
//import com.dians.hotelmanagement.model.Hotel;
//import com.dians.hotelmanagement.model.User;
//import com.dians.hotelmanagement.service.FeedbackService;
//import com.dians.hotelmanagement.service.HotelService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//@RequestMapping("/review")
//public class ReviewController {
//    private final FeedbackService feedbackService;
//    private final HotelService hotelService;
//    public ReviewController(FeedbackService feedbackService, HotelService hotelService) {
//        this.feedbackService = feedbackService;
//        this.hotelService = hotelService;
//    }
//
//    @GetMapping(value="/add/{hotelId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public RedirectView addReview(@PathVariable Long hotelId,
//                                  @RequestParam String reviewText,
//                                  @RequestParam Integer stars,
//                                  @RequestParam String cityName){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        feedbackService.addFeedbackToHotel(user.getEmail(), hotelId, reviewText, stars);
//        String hotelName=hotelService.findById(hotelId).get().getName();
//        return new RedirectView("/hotel/"+hotelName+"?city="+cityName);
//    }
//}
