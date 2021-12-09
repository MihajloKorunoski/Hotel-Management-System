package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.repository.CityRepository;
import com.dians.hotelmanagement.repository.HotelRepository;
import com.dians.hotelmanagement.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImplementation implements CityService {
    private final HotelRepository hotelRepository;
    public CityServiceImplementation(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getAllHotelsInCity(String cityName) {
        return this.hotelRepository.findAll()
                .stream()
                .filter(h -> h.getCity()
                        .getName()
                        .equals(cityName))
                .collect(Collectors.toList());
    }
}
