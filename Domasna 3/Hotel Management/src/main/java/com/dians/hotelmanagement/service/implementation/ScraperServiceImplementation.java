package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.repository.CityRepository;
import com.dians.hotelmanagement.repository.HotelRepository;
import com.dians.hotelmanagement.repository.ScraperRepository;
import com.dians.hotelmanagement.service.ScraperService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScraperServiceImplementation implements ScraperService {
    private final ScraperRepository scraperRepository;
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    public ScraperServiceImplementation(ScraperRepository scraperRepository, CityRepository cityRepository, HotelRepository hotelRepository) {
        this.scraperRepository = scraperRepository;
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void populateDatabase() throws IOException {
        //this.cityRepository.saveAll(this.scraperRepository.getAllCities());
        //this.hotelRepository.saveAll(this.scraperRepository.getAllHotelsInCity());
    }
}
