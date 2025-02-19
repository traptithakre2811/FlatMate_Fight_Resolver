package com.flatmate.repository;

import com.flatmate.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlatRepo extends JpaRepository<Flat,Long> {
    public Optional<Flat> findByFlatCode(String flatCode);
}
