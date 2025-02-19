package com.flatmate.repository;

import com.flatmate.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint,Long> {

    public Optional<Complaint> findByIdAndUser_Id(Long complainerUserId,Long complaintId);

    public List<Complaint> findByResolveFalse();
}
