package com.dians.hotelmanagement.service;


import com.dians.hotelmanagement.model.Hotel;

import java.util.List;

public interface CityService {
    List<Hotel> getAllHotelsInCity(String cityName);
}
