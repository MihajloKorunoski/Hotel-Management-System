package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.repository.UserRepository;
import com.dians.hotelmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class UserController {


    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage() {
        return "login";
    }

    @PostMapping
    public String saveLogin(@RequestParam String email, @RequestParam String password){


        return "home";
    }

    //post mapping -> prati rabote sto ke se unesev u imputs. Od ovdeka napravi service (user service) impl i tamo ke napravis save do user repo.
}
