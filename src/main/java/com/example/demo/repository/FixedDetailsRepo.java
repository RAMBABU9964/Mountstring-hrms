package com.example.demo.repository;

import com.example.demo.model.FixedDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface FixedDetailsRepo extends JpaRepository<FixedDetails, Long> {
    @Query("SELECT fd.fixedinTime FROM FixedDetails fd WHERE fd.id = (SELECT MAX(fd2.id) FROM FixedDetails fd2)")
    LocalTime getCurrentFixedTimeFromDatabase();

    @Query("SELECT fd.fixedOutTime FROM FixedDetails fd WHERE fd.id = (SELECT MAX(fd2.id) FROM FixedDetails fd2)")
    LocalTime getCurrentFixedOutTimeFromDatabase();

    @Query("SELECT d FROM FixedDetails d ORDER BY d.createdAt DESC")
    FixedDetails findLatestRecord();
    @Query("SELECT d FROM FixedDetails d WHERE d.id = (SELECT MAX(d2.id) FROM FixedDetails d2)")
    Optional<FixedDetails> findLatestRecordWithId();

    
    List<FixedDetails> findByCreatedAt(LocalDate createdAt);

	
	
}
