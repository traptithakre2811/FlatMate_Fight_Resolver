package com.flatmate.service;

import com.flatmate.dto.VotingRequestDto;
import org.springframework.http.ResponseEntity;

public interface VotingService {

    public ResponseEntity<?> voteForComplain( VotingRequestDto votingRequestDto);

    public ResponseEntity<?> fetchMostVotedComplaint(Long flatId);
}
