package com.dians.hotelmanagement.repository;


import com.dians.hotelmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}