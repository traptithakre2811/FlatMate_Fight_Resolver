package com.flatmate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CommonResponse {
    private String message;
    private Integer code;
    private Object data;


}
