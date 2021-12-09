package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;

import java.io.IOException;
import java.util.*;


public interface ScraperService {
    void populateDatabase() throws IOException;
}
