package com.alexandru.SpringBootStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping/user")
public class UserController {

    //ACCOUNT SETTINGS
    @GetMapping("/settings")
    public String accountSettings() {
        return "account_settings";
    }


    //Change name
    //Change email
    //Change password

    //Address add/change

    //ORDERS

    //LOGOUT


}
