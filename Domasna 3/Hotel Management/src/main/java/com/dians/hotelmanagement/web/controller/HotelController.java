package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Controller
@RequestMapping(value="/hotel")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @GetMapping(value="/{name}")
    public String getHomePage(@RequestParam String city, @PathVariable String name, Model model) throws IOException {
        Hotel hotel = this.hotelService.findHotelByCityNameAndHotelName(city, name);
        model.addAttribute("hotel", hotel);
        model.addAttribute("longitude", hotel.getLongitude());
        model.addAttribute("latitude", hotel.getLatitude());
        model.addAttribute("hotelName", hotel.getName());
        model.addAttribute("bodyContent","hotel");
        return "master-template";
    }
}
