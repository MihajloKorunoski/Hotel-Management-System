package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.repository.HotelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(value="/hotel")
public class HotelController {
    private final HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping(value="/{name}")
    public String getHomePage(@RequestParam String city, @PathVariable String name, Model model) {
        Optional<Hotel> hotel =  this.hotelRepository.findAll()
                .stream()
                .filter(h->h.getCity().getName().equals(city) && h.getName().equals(name))
                .findFirst();
        hotel.ifPresent(value -> model.addAttribute("hotel", value));
        //this.hotelRepository.
        return "hotel";
    }
}
