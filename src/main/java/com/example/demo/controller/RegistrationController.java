package com.example.demo.controller;

import com.example.demo.util.request.RegistrationRequest;
import com.example.demo.service.RegistrationService;
import com.example.demo.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final RoleService roleService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token) {
        System.out.println("token : " + token);
        return registrationService.confirmToken(token);
    }

    @GetMapping(path="getRoles")
    public String findRoles(@RequestParam("userId") Long userId) {
        System.out.println("id : " + userId);
        return  roleService.findAppUserRole(userId).toString();
    }


}
