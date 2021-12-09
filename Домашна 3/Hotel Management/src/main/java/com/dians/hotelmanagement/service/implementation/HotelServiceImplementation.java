package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.repository.HotelRepository;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImplementation implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImplementation(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    @Override
    public List<Hotel> findAll() {
        return this.hotelRepository.findAll();
    }
    @Override
    public List<Hotel> findAllHotelsInCity(String cityName) {
        return (long) this.hotelRepository.findAllByCityName(cityName).size() ==0 ?
                this.hotelRepository.findAllByName(cityName) : this.hotelRepository.findAllByCityName(cityName);
    }
    @Override
    public List<Hotel> findMostVisitedHotels() {
        return this.hotelRepository.findMostVisitedHotels();
    }
    @Override
    public Hotel findHotelByCityNameAndHotelName(String cityName, String hotelName) {
        return this.hotelRepository.findHotelByCityNameAndName(cityName, hotelName);
    }
    @Override
    public void save(Hotel hotel) {
        this.hotelRepository.save(hotel);
    }

}
