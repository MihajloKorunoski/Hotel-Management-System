package com.dians.hotelmanagement.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @GetMapping
    public String getLoginPage(Model model)
    {
        model.addAttribute("bodyContent","login");
        return "master-template";
    }
    @GetMapping(value="/register")
    public String getRegisterPage(Model model)
    {
        model.addAttribute("bodyContent","register");
        return "master-template";
    }
}
