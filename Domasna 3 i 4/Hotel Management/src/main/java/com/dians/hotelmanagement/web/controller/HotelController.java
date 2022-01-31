package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.service.FeedbackService;
import com.dians.hotelmanagement.service.HotelService;
import com.dians.hotelmanagement.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final FeedbackService feedbackService;
    private final UserService userService;

    public HotelController(HotelService hotelService, FeedbackService feedbackService, UserService userService) {
        this.hotelService = hotelService;
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @GetMapping(value = "/{name}")
    public String getHomePage(@RequestParam String city, @PathVariable String name, Model model) throws IOException {
        Hotel hotel = this.hotelService.findHotelByCityNameAndHotelName(city, name);
        model.addAttribute("hotel", hotel);
        model.addAttribute("longitude", hotel.getLongitude());
        model.addAttribute("latitude", hotel.getLatitude());
        model.addAttribute("hotelName", hotel.getName());
        model.addAttribute("bodyContent", "hotel");
        model.addAttribute("reviews", feedbackService.listAllFeedbacksForHotel(hotel.getId()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        }
        model.addAttribute("hasAlreadyRatedTheHotel", user != null &&
                userService.findByEmail(user.getEmail())
                        .getFeedbacks()
                        .stream()
                        .anyMatch(t -> Objects.equals(t.getHotel().getId(), hotel.getId())));
        return "master-template";
    }
}
