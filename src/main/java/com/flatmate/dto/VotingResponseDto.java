package com.flatmate.dto;

import com.flatmate.entity.Complaint;
import com.flatmate.entity.User;
import com.flatmate.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingResponseDto {

    private VoteType voteType;
    private String votedBy;
}
