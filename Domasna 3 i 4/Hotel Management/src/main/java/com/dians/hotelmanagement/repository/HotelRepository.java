package com.dians.hotelmanagement.repository;

import com.dians.hotelmanagement.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query(value = "select * from dians.hotel h inner join dians.city c on c.id = h.city_id where c.name ilike ?1", nativeQuery = true)

    Page<Hotel> findAllByCityName(String cityName, Pageable pageable);


    @Query(value = "select * from dians.hotel h inner join dians.city c on c.id = h.city_id where c.name ilike ?1", nativeQuery = true)
    List<Hotel> findAllByCityName(String cityName);

    Hotel findHotelByCityNameAndName(String cityName, String hotelName);
}
