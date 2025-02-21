package com.flatmate.service;

import com.flatmate.dto.FlatRequestDto;
import org.springframework.http.ResponseEntity;

public interface FlatService {

   public ResponseEntity<?> registerFlat(FlatRequestDto requestDto);

    public ResponseEntity<?> getFlatStats(Long userId);
}
