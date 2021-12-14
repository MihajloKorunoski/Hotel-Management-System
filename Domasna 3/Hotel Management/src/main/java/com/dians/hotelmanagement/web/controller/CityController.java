package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.service.CityService;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/hotels/{name}")
public class CityController {
    private final HotelService hotelService;
    private final CityService cityService;
    public CityController(HotelService hotelService, CityService cityService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
    }

    @GetMapping()
    public String getCityPage(@PathVariable String name, @RequestParam Optional<Integer> page, Model model) {
        model.addAttribute("city", name);
        int currentPage = page.orElse(1);
        int pageSize = 4;
        Page<Hotel> hotelsInCity = this.hotelService.findAllHotelsInCity(name, PageRequest.of(currentPage-1,pageSize));

        int totalPages = hotelsInCity.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("currentPage",currentPage);
        Double[] longitudes = new Double[hotelsInCity.getContent().size()];
        Double[] latitudes = new Double[hotelsInCity.getContent().size()];
        String[] hotelNames = new String[hotelsInCity.getContent().size()];
        IntStream.range(0, hotelsInCity.getContent().size())
                        .forEach(i -> {
                            longitudes[i] = hotelsInCity.getContent().get(i).getLongitude();
                            latitudes[i] = hotelsInCity.getContent().get(i).getLatitude();
                            hotelNames[i] = hotelsInCity.getContent().get(i).getName();
                        });
        model.addAttribute("longitudes", longitudes);
        model.addAttribute("latitudes", latitudes);
        model.addAttribute("hotelNames", hotelNames);

        model.addAttribute("hotels", hotelsInCity);
        model.addAttribute("bodyContent", "city");
        return "master-template";
    }
}
