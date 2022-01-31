package com.dians.hotelmanagement.service;

import com.dians.hotelmanagement.model.User;

public interface AuthService {
    User login(String email, String password);
}
