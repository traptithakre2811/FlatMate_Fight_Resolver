package com.flatmate.dto;

import com.flatmate.entity.Flat;
import com.flatmate.enums.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PunishmentResponse {
    private PunishmentType punishmentType;
    private String userName;
    private String flatCode;
}
