package com.dians.hotelmanagement.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Hotel hotel;
    private String reviewText;
    private int stars;

    public Feedback(User user, Hotel hotel, String reviewText, int stars) {
        this.user = user;
        this.hotel = hotel;
        this.reviewText = reviewText;
        this.stars = stars;
    }

    public Feedback() {
    }
}
