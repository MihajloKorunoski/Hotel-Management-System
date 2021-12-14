package com.dians.hotelmanagement.repository;

import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Page<Hotel> findAllByCityName(String cityName, Pageable pageable);
    @Query(value="select * from dians.hotel order by times_visited limit 3",nativeQuery = true)
    List<Hotel> findMostVisitedHotels();
    @Query(value="select * from dians.hotel where hotel.name ilike %:hotelName%", nativeQuery = true)
    List<Hotel> findAllByName(@Param("hotelName") String hotelName);

    Hotel findHotelByCityNameAndName(String cityName, String hotelName);
}
