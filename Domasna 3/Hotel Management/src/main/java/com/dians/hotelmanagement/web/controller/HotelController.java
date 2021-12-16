package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.FeedbackService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
@RequestMapping(value="/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final FeedbackService feedbackService;
    public HotelController(HotelService hotelService, FeedbackService feedbackService) {
        this.hotelService = hotelService;
        this.feedbackService = feedbackService;
    }
    @GetMapping(value="/{name}")
    public String getHomePage(@RequestParam String city, @PathVariable String name, Model model) throws IOException {
        Hotel hotel = this.hotelService.findHotelByCityNameAndHotelName(city, name);
        model.addAttribute("hotel", hotel);
        model.addAttribute("longitude", hotel.getLongitude());
        model.addAttribute("latitude", hotel.getLatitude());
        model.addAttribute("hotelName", hotel.getName());
        model.addAttribute("bodyContent","hotel");
        model.addAttribute("reviews",feedbackService.listAllFeedbacksForHotel(hotel.getId()));
        return "master-template";
    }
}
