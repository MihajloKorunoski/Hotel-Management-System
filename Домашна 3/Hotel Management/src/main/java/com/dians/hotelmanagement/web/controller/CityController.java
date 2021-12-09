package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.CityService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/hotels/{name}")
public class CityController {
    private final HotelService hotelService;

    public CityController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping()
    public String getCityPage(@PathVariable String name, Model model) {
        model.addAttribute("city", name);
        List<Hotel> hotelsInCity = this.hotelService.findAllHotelsInCity(name);
        model.addAttribute("hotels", hotelsInCity);
        return "city";
    }
}
