package com.flatmate.dto;

import com.flatmate.enums.ComplaintType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatStatsResponse {
    private int totalComplaint;
    private ComplaintType setMostCommonComplaintType;
    private String mostComplainedAgainstUser;
}
