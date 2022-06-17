package com.example.demo.controller;

import com.example.demo.util.request.RegistrationRequest;
import com.example.demo.service.impl.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token) {
        System.out.println("token : " + token);
        return registrationService.confirmToken(token);
    }
}
