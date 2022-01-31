package com.dians.hotelmanagement.web.rest;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hotels")
public class HotelsRestController {
    private final HotelService hotelService;

    public HotelsRestController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    //Returns list of hotels for populating the map with all the hotels location in given city
    @GetMapping
    public List<Hotel> findAll(@RequestParam String cityName) {
        return this.hotelService.findAllHotelsInCity(cityName);
    }
}