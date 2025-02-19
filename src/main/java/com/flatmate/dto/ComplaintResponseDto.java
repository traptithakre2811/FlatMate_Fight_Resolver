package com.flatmate.dto;

import com.flatmate.enums.ComplaintType;
import com.flatmate.enums.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComplaintResponseDto {
    private Long id;

    private String title;

    private String description;

    private ComplaintType complaintType;

    private SeverityLevel severityLevel;

    private boolean resolve;

    private Long userId;

    private Long againstUserId;


}
