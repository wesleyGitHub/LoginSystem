package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path ="/api")
public class UserController {
    private final UserService userService;

    @GetMapping(path ="/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @GetMapping(path = "/getUsers2")
    public List<User> getAllStudents() {
        return userService.getUsers();
    }

    @PostMapping(path = "/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return null;
    }
}
