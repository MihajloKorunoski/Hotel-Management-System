package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll();
    Page<Hotel> findAllHotelsInCity(String cityName, Pageable pageable);
    List<Hotel> findMostVisitedHotels();
    Hotel findHotelByCityNameAndHotelName(String cityName, String hotelName);
    void save(Hotel hotel);
}
