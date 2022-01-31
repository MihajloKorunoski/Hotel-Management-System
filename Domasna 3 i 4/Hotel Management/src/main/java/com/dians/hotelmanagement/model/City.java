package com.dians.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    private String website;

    @OneToMany(mappedBy = "city")
    @JsonBackReference
    private List<Hotel> hotels;

    public City() {
    }

    public City(String name, String website) {
        this.name = name;
        this.website = website;
    }
}
