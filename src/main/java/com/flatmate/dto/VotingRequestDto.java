package com.flatmate.dto;

import com.flatmate.entity.Complaint;
import com.flatmate.entity.User;
import com.flatmate.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingRequestDto {


    private VoteType voteType;

    private Long complaintId;

    private Long userId;
}
