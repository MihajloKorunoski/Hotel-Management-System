package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.CityService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/")
public class HomeController {
    private final CityService cityService;
    private final HotelService hotelService;

    public HomeController(CityService cityService, HotelService hotelService) {
        this.cityService = cityService;
        this.hotelService = hotelService;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String getHomePage(Model model, @RequestParam(required = false) String searchText) {
        if (searchText != null && !searchText.isEmpty()) {
            List<Hotel> hotelsInCity = this.hotelService.findAllHotelsInCity(searchText);
            model.addAttribute("city", searchText);
            model.addAttribute("hotels", hotelsInCity);
            return "city";
        }
        List<City> cities = this.cityService.findAll();
        model.addAttribute("cities", cities);
        List<Hotel> mostVisitedHotels = this.hotelService.findMostVisitedHotels();
        model.addAttribute("mostVisitedHotels", mostVisitedHotels);
        model.addAttribute("bodyContent","home");
        return "master-template";
    }
}
