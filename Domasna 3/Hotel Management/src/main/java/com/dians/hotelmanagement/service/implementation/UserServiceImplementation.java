package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.repository.UserRepository;
import com.dians.hotelmanagement.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user){
        this.userRepository.save(user);
    };
}
