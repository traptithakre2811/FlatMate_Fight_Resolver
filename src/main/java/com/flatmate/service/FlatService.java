package com.flatmate.service;

import com.flatmate.dto.FlatRequestDto;
import org.springframework.http.ResponseEntity;

public interface FlatService {

    ResponseEntity<?> registerFlat(FlatRequestDto requestDto);
}
