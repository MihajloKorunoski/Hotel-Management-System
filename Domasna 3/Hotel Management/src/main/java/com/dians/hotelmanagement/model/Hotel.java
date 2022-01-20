package com.dians.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Hotel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=100)
    private String name;
    @Column(length=20000)
    private String description;
    @Column(length=500)
    private String address;
    @Column(length=20)
    private String phone;
    @Column(length=20)
    private String fax;
    @Column(length=2000)
    private String website;
    @Column(length=1000)
    private String facilities;
    @Column(length=1000)
    private String roomFacilities;
    @Column(length=20)
    private String checkIn;
    @Column(length=20)
    private String checkOut;
    @Column(length=30)
    private String petsAllowedInfo;
    @Column(length=500)
    private String centerDistanceDescription;
    private Integer starsCount;
    private Integer timesVisited;
    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JsonManagedReference
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    private City city;

    @OneToMany(mappedBy = "hotel")
    @JsonBackReference
    List<Feedback> feedbacks;

    private Double longitude;
    private Double latitude;
    public Hotel() {}

    public Hotel(String name, String description, String address, String phone, String fax,
                 String website, String facilities, String roomFacilities, String checkIn, String checkOut,
                 String petsAllowedInfo, String centerDistanceDescription, Integer starsCount, List<String> images,
                 Double longitude, Double latitude){
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.website = website;
        this.facilities = facilities;
        this.roomFacilities = roomFacilities;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.petsAllowedInfo = petsAllowedInfo;
        this.centerDistanceDescription = centerDistanceDescription;
        this.starsCount = starsCount;
        this.images = images;
        this.timesVisited = 0;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
