package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.repository.CityRepository;
import com.dians.hotelmanagement.repository.HotelRepository;
import com.dians.hotelmanagement.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping(value = "/")
public class HomeController {
    private final CityService cityService;
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;

    public HomeController(CityService cityService, CityRepository cityRepository, HotelRepository hotelRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String getHomePage(Model model, @RequestParam(required = false) String searchText) {
        if (searchText != null && !searchText.isEmpty()) {
            model.addAttribute("city", searchText);
            List<Hotel> hh = this.cityService.getAllHotelsInCity(searchText);
            model.addAttribute("hotels", hh);
            return "city";
        }
        List<City> cities = this.cityRepository.findAll();
        model.addAttribute("cities", cities);
        List<Hotel> mostVisitedHotels = this.hotelRepository.findAll();
        mostVisitedHotels = mostVisitedHotels.stream()
                .sorted(Comparator.comparing(Hotel::getTimesVisited))
                .limit(3)
                .collect(Collectors.toList());
        model.addAttribute("mostVisitedHotels", mostVisitedHotels);

        return "home";
    }

    @GetMapping(value = "/{name}")
    public String getCityPage(@PathVariable String name, Model model) {
        model.addAttribute("city", name);
        List<Hotel> hh = this.cityService.getAllHotelsInCity(name);
        model.addAttribute("hotels", hh);
        return "city";
    }
}
