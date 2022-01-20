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
//    @Query(value="select h.*, sum(f.stars) as total_stars from dians.hotel h left join" +
//            " dians.feedback f on h.id = f.hotel_id " +
//            "group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18 order by total_stars desc limit 3",nativeQuery = true)
    @Query(value="select * from dians.hotel", nativeQuery = true)
    List<Hotel> findMostPopularHotels();
    //@Query(value="select * from dians.hotel where hotel.name ilike %:hotelName%", nativeQuery = true)
    //List<Hotel> findAllByName(@Param("hotelName") String hotelName);
    List<Hotel> findAllByCityName(String cityName);
    Hotel findHotelByCityNameAndName(String cityName, String hotelName);
}
