package com.chakrayFZRD.users_api.controller;

import com.chakrayFZRD.users_api.model.User;
import com.chakrayFZRD.users_api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/ricky-api/users") // ruta para mi controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
     }

     @GetMapping
     public List<User> getUsers() {
         return userService.getAllUsers();
     }
    
}
