package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.CityService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping(value = "/")
public class HomeController {
    private final CityService cityService;
    private final HotelService hotelService;

    public HomeController(CityService cityService, HotelService hotelService) {
        this.cityService = cityService;
        this.hotelService = hotelService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        List<City> cities = this.cityService.findAll();
        model.addAttribute("cities", cities);
        List<Hotel> mostPopular = this.hotelService.findMostPopularHotels();
        model.addAttribute("mostPopular", mostPopular);
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }
}
