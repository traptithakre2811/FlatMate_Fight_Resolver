package com.flatmate.service;

import com.flatmate.dto.LoginReqDto;
import com.flatmate.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

public ResponseEntity<?> registerFlatmate(UserRequestDto userRequestDto);

public ResponseEntity<?> login(LoginReqDto loginReqDto);

}
