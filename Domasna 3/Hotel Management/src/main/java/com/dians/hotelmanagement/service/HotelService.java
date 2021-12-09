package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll();
    List<Hotel> findAllHotelsInCity(String cityName);
    List<Hotel> findMostVisitedHotels();
    Hotel findHotelByCityNameAndHotelName(String cityName, String hotelName);
    void save(Hotel hotel);
}
