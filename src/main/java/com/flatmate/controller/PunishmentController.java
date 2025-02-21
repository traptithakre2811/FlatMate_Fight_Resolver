package com.flatmate.controller;

import com.flatmate.service.PunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PunishmentController {
    @Autowired
    private PunishmentService punishmentService;

    @GetMapping("/getPunish/{flatId}")
    public ResponseEntity<?> punishemt(@PathVariable Long flatId) {
        return punishmentService.punishemt(flatId);
    }
}
