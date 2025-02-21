package com.flatmate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShowKarmaPointResponseDto {

    private String userName;
    private int karmaPoint;
    private String flatCode;
}
