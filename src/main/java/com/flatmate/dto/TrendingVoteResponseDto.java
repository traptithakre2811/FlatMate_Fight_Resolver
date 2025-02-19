package com.flatmate.dto;

import com.flatmate.enums.ComplaintType;
import com.flatmate.enums.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingVoteResponseDto {
    private Long id;

    private String title;

    private String description;

    private ComplaintType complaintType;

    private SeverityLevel severityLevel;

    private Boolean resolved;

    private Long upVotes;

    private Long downVotes;

    private Long netVotes;




}
