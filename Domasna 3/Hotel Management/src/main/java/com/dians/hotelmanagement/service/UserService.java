package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String email, String firstName, String lastName, String password, String confirmPassword);
    User findByEmail(String email);
}
