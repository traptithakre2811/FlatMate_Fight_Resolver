package com.flatmate.controller;

import com.flatmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderBoardAndStatsController {

    @Autowired
    private UserService userService;

    @GetMapping(name="SHOW_KARMA_POINT",value = "/karmarank/{userId}")
    public ResponseEntity<?> showKarmaPointRanking(@PathVariable Long userId) {

        ResponseEntity<?> responseEntity = userService.showKarmaPointRanking(userId);
        System.err.println("11");
        return responseEntity;
    }
}
