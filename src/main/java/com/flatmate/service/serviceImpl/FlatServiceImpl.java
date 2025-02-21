package com.flatmate.service.serviceImpl;

import com.flatmate.dto.FlatRequestDto;
import com.flatmate.dto.FlatResponseDto;
import com.flatmate.dto.FlatStatsResponse;
import com.flatmate.entity.Complaint;
import com.flatmate.entity.Flat;
import com.flatmate.entity.User;
import com.flatmate.enums.ComplaintType;
import com.flatmate.exceptionhandler.customeexception.WrongInputException;
import com.flatmate.repository.ComplaintRepo;
import com.flatmate.repository.FlatRepo;
import com.flatmate.repository.UserRepo;
import com.flatmate.response.CommonResponse;
import com.flatmate.service.FlatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlatServiceImpl implements FlatService {

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ComplaintRepo complaintRepo;

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

    @Override
    public ResponseEntity<?> getFlatStats(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the flat of the logged-in user
        Flat flat = user.getFlat();

        // Fetch all complaints in the flat
        List<Complaint> complaints = complaintRepo.findByFlatId(flat.getId());

        // Calculate total complaints
        int totalComplaints = complaints.size();

        // Calculate most common complaint type
        Map<ComplaintType, Long> complaintTypeCounts = complaints.stream()
                .collect(Collectors.groupingBy(Complaint::getComplaintType, Collectors.counting()));
        ComplaintType mostCommonComplaintType = Collections.max(complaintTypeCounts.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Calculate most complained-against user
        Map<User, Long> complainedAgainstUserCounts = complaints.stream()
                .collect(Collectors.groupingBy(Complaint::getAgainstUser, Collectors.counting()));
        User mostComplainedAgainstUser = Collections.max(complainedAgainstUserCounts.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Build response
        FlatStatsResponse response = new FlatStatsResponse();
        response.setTotalComplaint(totalComplaints);
        response.setSetMostCommonComplaintType(mostCommonComplaintType);
        response.setMostComplainedAgainstUser(mostComplainedAgainstUser.getUsername());
        return ResponseEntity.ok(new CommonResponse("flat stat successfully fetch",200,response));
    }
}
