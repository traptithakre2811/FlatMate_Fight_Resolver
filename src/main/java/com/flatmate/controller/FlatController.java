package com.flatmate.controller;

import com.flatmate.dto.FlatRequestDto;
import com.flatmate.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flat")
public class FlatController {
    @Autowired
    private FlatService flatService;

    @PostMapping(name = "Register_Flat",value = "/register")
    public ResponseEntity<?> registerFlat(FlatRequestDto requestDto) {
       return flatService.registerFlat(requestDto);
    }

    @GetMapping(name="Get_FlatStats",value = "/stats/{userId}")
    public ResponseEntity<?> getFlatStats(@PathVariable Long userId) {
          return flatService.getFlatStats(userId);
    }
}
