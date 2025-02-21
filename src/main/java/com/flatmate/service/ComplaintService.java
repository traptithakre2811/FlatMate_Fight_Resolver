package com.flatmate.service;

import com.flatmate.dto.ComplaintRequestDto;
import org.springframework.http.ResponseEntity;

public interface ComplaintService {
    public ResponseEntity<?> registerComplaint(ComplaintRequestDto complaintRequestDto);
    public ResponseEntity<?> complaintResolved(Long complaintUserId , Long againstUserId );

    public ResponseEntity<?> getAllActiveComplaint();


}
