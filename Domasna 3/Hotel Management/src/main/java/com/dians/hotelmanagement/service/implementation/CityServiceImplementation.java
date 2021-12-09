package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.repository.CityRepository;
import com.dians.hotelmanagement.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImplementation implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImplementation(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAll() {
        return this.cityRepository.findAll();
    }

    @Override
    public City findByName(String cityName) {
        return this.cityRepository.findByName(cityName);
    }

    @Override
    public void save(City city) {
        this.cityRepository.save(city);
    }
}
