package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.model.exceptions.InvalidArgumentsException;
import com.dians.hotelmanagement.model.exceptions.InvalidUserCredentialsException;
import com.dians.hotelmanagement.repository.UserRepository;
import com.dians.hotelmanagement.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String email, String password) {
        if (email==null || email.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

}
