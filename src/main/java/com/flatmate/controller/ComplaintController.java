package com.flatmate.controller;

import com.flatmate.dto.ComplaintRequestDto;
import com.flatmate.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;


    @PostMapping(name = "REGISTER_COMPLAINT", value = "/filed")
    public ResponseEntity<?> registerComplaint(ComplaintRequestDto complaintRequestDto) {
        return complaintService.registerComplaint(complaintRequestDto);
    }

    @PutMapping(name = "COMPLAINT_RESOLVED", value = "/{complaintUserId}/{complaintId}/resolve")
    public ResponseEntity<?> complaintResolved(@PathVariable Long complaintUserId, @PathVariable Long complaintId) {
        return complaintService.complaintResolved(complaintUserId, complaintId);
    }

    @GetMapping(name = "ACTIVE_COMPLAINTS", value = "all/active/complaints")
    public ResponseEntity<?> activeComplaints() {
           return complaintService.getAllActiveComplaint();
    }
}