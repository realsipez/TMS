package com.tms.controller;

import com.tms.dto.user.LoginRequest;
import com.tms.dto.user.LoginResponse;
import com.tms.dto.user.RegistrationRequest;
import com.tms.dto.user.RegistrationResponse;
import com.tms.exception.RoleNotFoundException;
import com.tms.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest registrationRequest) throws RoleNotFoundException {
        RegistrationResponse response = userService.register(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/setToAdmin")
    public ResponseEntity<String> setToAdmin(@RequestParam String username) throws RoleNotFoundException {
        String response = userService.setRoleToAdmin(username);
        return ResponseEntity.ok(response);
    }
}
