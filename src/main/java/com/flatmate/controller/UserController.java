package com.flatmate.controller;

import com.flatmate.dto.UserRequestDto;
import com.flatmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(name ="REGISTER_FLATMATE",value = "/register")
    public ResponseEntity<?> registerFlatmate(@RequestBody UserRequestDto userRequestDto) {
        return userService.registerFlatmate(userRequestDto);
    }


}
