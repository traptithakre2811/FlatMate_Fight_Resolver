package com.flatmate.controller;

import com.flatmate.dto.VotingRequestDto;
import com.flatmate.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
public class VotingController {

    @Autowired
    private VotingService votingService;
    @PostMapping(name="VOTE_FOR_COMPLAIN",value = "/vote")
    public ResponseEntity<?> voteForComplain(@RequestBody VotingRequestDto votingRequestDto) {
        return votingService.voteForComplain(votingRequestDto);
    }

    @GetMapping(name="FETCH_MOST_VOTED_COMPLAINT",value = "/trending")
    public ResponseEntity<?> fetchMostVotedComplaint(@RequestParam Long flatId) {
        return votingService.fetchMostVotedComplaint(flatId);
    }


}
