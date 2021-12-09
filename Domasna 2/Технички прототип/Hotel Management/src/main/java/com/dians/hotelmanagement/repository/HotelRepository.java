package com.dians.hotelmanagement.repository;

import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> getAllHotelsByCityName(String cityName);
    List<Hotel> findAllByName(String name);
//    @Query(value="update dians.hotel h set h.timesVisited=5",nativeQuery = true)
//    void updateTimesVisited(String hotelName,Integer count);
}
