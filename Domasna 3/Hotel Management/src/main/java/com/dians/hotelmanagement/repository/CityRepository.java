package com.dians.hotelmanagement.repository;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
