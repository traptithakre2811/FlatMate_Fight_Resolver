package com.flatmate.repository;

import com.flatmate.entity.Complaint;
import com.flatmate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint,Long> {

    public Optional<Complaint> findByIdAndUser_Id(Long complainerUserId,Long complaintId);

    public List<Complaint> findByResolveFalse();

//    @Query("SELECT c FROM Complaint c WHERE c.user.id IN :userIds")
//    public List<User> userList(@Param("userIds") List<Long> userIds);

    public List<Complaint> findByUserIdIn(List<Long> userId );

    @Query("SELECT c FROM Complaint c WHERE c.user.flat.id = :flatId")
    List<Complaint> findByFlatId(@Param("flatId") Long flatId);
}
