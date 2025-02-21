package com.flatmate.service.serviceImpl;

import com.flatmate.dto.ComplaintRequestDto;
import com.flatmate.dto.ComplaintResponseDto;
import com.flatmate.entity.Complaint;
import com.flatmate.entity.User;
import com.flatmate.enums.SeverityLevel;
import com.flatmate.exceptionhandler.customeexception.NotFoundException;
import com.flatmate.exceptionhandler.customeexception.WrongInputException;
import com.flatmate.repository.ComplaintRepo;
import com.flatmate.repository.UserRepo;
import com.flatmate.response.CommonResponse;
import com.flatmate.service.ComplaintService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepo complaintRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> registerComplaint(ComplaintRequestDto complaintRequestDto) {
        Optional<User> complaintUser = userRepo.findById(complaintRequestDto.getUserId());
        if (!complaintUser.isPresent()) {
            throw new NotFoundException(404, "complaint user is not found");
        }
        Optional<User> againstUser = userRepo.findById(complaintRequestDto.getAgainstUserId());
        if (!againstUser.isPresent()) {
            throw new NotFoundException(404, "against user is not found");
        }
        if (!complaintUser.get().getFlat().getFlatCode().equals(againstUser.get().getFlat().getFlatCode())) {
            throw new WrongInputException(400, "flatmate is wrong");
        }
        if (complaintUser.get().getComplaints() == againstUser.get().getComplaints()) {
            throw new WrongInputException(400, "your not file complaint against self");
        }
        Complaint complaint = modelMapper.map(complaintRequestDto, Complaint.class);
        complaint.setComplaintId(null);
        complaint.setUser(complaintUser.get());
        complaint.setAgainstUser(againstUser.get());
        complaint.setCreatedAt(LocalDateTime.now());
        Complaint complaintData = complaintRepo.save(complaint);
        ComplaintResponseDto responseDto = modelMapper.map(complaintData, ComplaintResponseDto.class);
        ComplaintResponseDto complaintResponseDto = modelMapper.map(complaint, ComplaintResponseDto.class);
        complaintResponseDto.setAgainstUserId(complaintResponseDto.getAgainstUserId());
        complaintRequestDto.setUserId(complaintRequestDto.getUserId());
        return ResponseEntity.ok(new CommonResponse("Complaint filed successfully", 200, responseDto));
    }

    @Override
    public ResponseEntity<?> complaintResolved(Long complaintUserId, Long complaintId) {
        Optional<Complaint> complaintByUser = complaintRepo.findByComplaintIdAndUser_Id(complaintId, complaintUserId);
        if (!complaintByUser.isPresent()) {
            throw new NotFoundException(404, "complaint or user does not exist");
        }
        Complaint complaint = complaintByUser.get();
        complaint.setResolve(true);
        Complaint complaintResolve = complaintRepo.save(complaint);
        int karmaPoint = calculateKarmaPoints(complaint.getSeverityLevel());
        User user = complaint.getUser();
        user.setKarmaPoints(user.getKarmaPoints()+karmaPoint);
        userRepo.save(user);
        ComplaintResponseDto complaintResponseDto = modelMapper.map(complaintResolve, ComplaintResponseDto.class);
        return ResponseEntity.ok(new CommonResponse("Complaint resolve successfully", 200, complaintResponseDto));
    }
    private int calculateKarmaPoints(SeverityLevel severityLevel) {
        // Define karma points based on severity level
        switch (severityLevel) {
            case MINOR:
                return 5;
            case ANNOYING:
                return 10;
            case MAJOR:
                return 15;
            case NUCLEAR:
                return 20;
            default:
                return 0;
        }
    }

    @Override
    public ResponseEntity<?> getAllActiveComplaint() {
        List<Complaint> activeComplaints = complaintRepo.findByResolveFalse();
        List<ComplaintResponseDto> responseDto = activeComplaints.stream()
                .map(complaint -> modelMapper.map(complaint, ComplaintResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse("Get all active complaits list", 200, responseDto));
    }


}
