package com.flatmate.controller;

import com.flatmate.dto.LoginReqDto;
import com.flatmate.dto.UserRequestDto;
import com.flatmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(name = "REGISTER_FLATMATE", value = "/register")
    public ResponseEntity<?> registerFlatmate(@RequestBody UserRequestDto userRequestDto) {
        return userService.registerFlatmate(userRequestDto);
    }

    @PostMapping(name = "LOGIN_API", value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginReqDto) {
        return userService.login(loginReqDto);
    }

    @GetMapping(name = "GET_BADGE", value = "/user/{flatId}/bagde")
    public ResponseEntity<?> getBadge(@PathVariable Long flatId) {
        return userService.getBestFLatMate(flatId);
    }


}
