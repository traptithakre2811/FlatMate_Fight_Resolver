package com.flatmate.service.serviceImpl;

import com.flatmate.dto.FlatRequestDto;
import com.flatmate.dto.FlatResponseDto;
import com.flatmate.entity.Flat;
import com.flatmate.exceptionhandler.customeexception.WrongInputException;
import com.flatmate.repository.FlatRepo;
import com.flatmate.response.CommonResponse;
import com.flatmate.service.FlatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlatServiceImpl implements FlatService {

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> registerFlat(FlatRequestDto requestDto) {
        if (flatRepo.findByFlatCode(requestDto.getFlatCode()).isPresent()) {
            throw new WrongInputException(400, "flatCode can not be duplicate");
        }
        Flat flat=new Flat();
        flat.setFlatCode(requestDto.getFlatCode());
        Flat registerFlat = flatRepo.save(flat);
        FlatResponseDto flatResponseDto = modelMapper.map(registerFlat, FlatResponseDto.class);
        return ResponseEntity.ok(new CommonResponse("flat successfully register",200,flatResponseDto));
    }
}
