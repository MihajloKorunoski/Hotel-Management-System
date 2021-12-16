package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.CityService;
import com.dians.hotelmanagement.service.HotelService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/hotels")
public class CityController {
    private final HotelService hotelService;
    private final CityService cityService;
    public CityController(HotelService hotelService, CityService cityService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getCityPage(@RequestParam String cityName, @RequestParam Optional<Integer> page
            , @RequestParam Optional<Integer> pageSize, Model model) {
        model.addAttribute("city", cityName);
        final int currentPage = page.orElse(1);
        final int currentPageSize = pageSize.orElse(5);
        Page<Hotel> hotelsInCity = this.hotelService.findAllHotelsInCity(cityName, PageRequest.of(currentPage-1,currentPageSize));

        int totalPages = hotelsInCity.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("hotels", hotelsInCity);
        model.addAttribute("bodyContent", "city");
        return "master-template";
    }
    @GetMapping(value="/bla")
    public String post(@RequestBody String body)
    {
        System.out.println("test");
        return "master-template";
    }


}
