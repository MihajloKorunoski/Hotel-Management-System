package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.FeedbackService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final FeedbackService feedbackService;
    private final HotelService hotelService;
    public ReviewController(FeedbackService feedbackService, HotelService hotelService) {
        this.feedbackService = feedbackService;
        this.hotelService = hotelService;
    }
    @PostMapping(value="/add")
    public String addReview(@RequestParam Long hotelId,
                            @RequestParam String reviewText,
                            @RequestParam int stars){
        //fali del za dobivanje na userId
        //feedbackService.addFeedbackToHotel(null,hotelId,reviewText,stars);
        String hotelName=hotelService.findById(hotelId).get().getName();
        return "redirect:/hotels/"+hotelName;
    }
    @GetMapping(value="/delete")
    public String deleteReview(@RequestParam Long feedbackId){
        String hotelName=feedbackService.findById(feedbackId).get().getHotel().getName();
        //fali del za dobivanje na userId
        //feedbackService.deleteFeedbackFromHotel(feedbackId,null);
        return "redirect:/hotels/"+hotelName;
    }
}
