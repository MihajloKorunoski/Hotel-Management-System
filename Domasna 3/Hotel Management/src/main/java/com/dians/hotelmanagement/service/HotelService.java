package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<Hotel> findAll();
    Page<Hotel> findAllHotelsInCity(String cityName, Pageable pageable);
    List<Hotel> findAllHotelsInCity(String cityName);
    List<Hotel> findMostPopularHotels();
    Hotel findHotelByCityNameAndHotelName(String cityName, String hotelName);
    void save(Hotel hotel);
    Optional<Hotel> findById(Long id);
}
