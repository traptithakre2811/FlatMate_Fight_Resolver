package com.flatmate.repository;

import com.flatmate.entity.Flat;
import com.flatmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.flat_id = :flatId AND u.karma_points > 0 ORDER BY u.karma_points DESC LIMIT 1", nativeQuery = true)
    Optional<User> findTopUserByFlatId(@Param("flatId") Long flatId);

    public List<User> findByFlat(Flat flat);

    //    public Flat findByFlat_Id(Long id);
    @Query(value = "SELECT * FROM users u WHERE u.flat_id = :flatId", nativeQuery = true)
    List<User> findUsersByFlatId(@Param("flatId") Long flatId);
}

