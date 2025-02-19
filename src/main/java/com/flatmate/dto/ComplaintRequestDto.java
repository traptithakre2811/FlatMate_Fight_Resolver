package com.flatmate.dto;

import com.flatmate.entity.User;
import com.flatmate.entity.Vote;
import com.flatmate.enums.ComplaintType;
import com.flatmate.enums.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComplaintRequestDto {
    private String title;

    private String description;

    private ComplaintType complaintType;

    private SeverityLevel severityLevel;

    private Long userId;

    private Long againstUserId;
}
