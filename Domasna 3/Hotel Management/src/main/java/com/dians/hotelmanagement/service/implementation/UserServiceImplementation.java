package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.model.enumerations.Role;
import com.dians.hotelmanagement.model.exceptions.InvalidArgumentsException;
import com.dians.hotelmanagement.model.exceptions.PasswordsDoNotMatchException;
import com.dians.hotelmanagement.model.exceptions.UsernameAlreadyExistsException;
import com.dians.hotelmanagement.repository.UserRepository;
import com.dians.hotelmanagement.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isNullOrEmpty(String value)
    {
        return value==null || value.isEmpty();
    }
    @Override
    public User register(String email, String firstName, String lastName, String password, String confirmPassword) {
        if(isNullOrEmpty(email) || isNullOrEmpty(firstName) || isNullOrEmpty(lastName) || isNullOrEmpty(password) || isNullOrEmpty(confirmPassword))
        {
            throw new InvalidArgumentsException();
        }
        if (!password.equals(confirmPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByEmail(email).isPresent())
            throw new UsernameAlreadyExistsException(email);
        User user = new User(email,passwordEncoder.encode(password),firstName,lastName);
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
