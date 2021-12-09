package com.dians.hotelmanagement.service;


import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;

import java.util.List;

public interface CityService {
    List<City> findAll();
    City findByName(String cityName);
    void save(City city);
}
