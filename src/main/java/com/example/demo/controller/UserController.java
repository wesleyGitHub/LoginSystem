package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path ="/api")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping(path ="/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        log.info(userService.getUsers().toString());
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @GetMapping(path = "/getUsers2")
    public List<User> getAllStudents() {
        return userService.getUsers();
    }

    @PostMapping(path = "/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("api/user/save")
                        .toUriString()
        );
        return ResponseEntity
                .created(uri)
                .body(userService.saveUser(user));

//        這個也可以但上面更進階精確
//        return ResponseEntity
//                .ok()
//                .body(userService.saveUser(user));
    }

    @PostMapping(path = "/role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("api/role/save")
                        .toUriString()
        );
        return ResponseEntity
                .created(uri)
                .body(userService.saveRole(role));
    }

    @PostMapping(path = "/role/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity
                .ok()
                .build();
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String rolename;
}
