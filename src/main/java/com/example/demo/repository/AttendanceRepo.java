package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Attendance;
import com.example.demo.model.User;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

	Optional<Attendance> findById(User user);

	@Query("SELECT a FROM Attendance a WHERE a.user.id = :userId")
   List<Attendance> findByUserId(long userId);
  
	
	//List<Attendance> findByUserIdAndDate(int userId, LocalDate indate);

	List<Attendance> findByDate(LocalDate date);

	
	@Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = :date")
	List<Attendance> findByUserIdAndDate(long userId, LocalDate date);

	@Query("SELECT a FROM Attendance a WHERE a.user.id = :userId")
Optional<Attendance> findTopByEmplyeeIdOrderByIdDesc(long userId);
	
	@Query("SELECT a.inTime FROM Attendance a WHERE a.user.id = :userId AND a.date = :todayDate")
	LocalTime findInTimeByUserIdAndDate(@Param("userId") long userId, @Param("todayDate") LocalDate todayDate);

	@Query("SELECT a.outTime FROM Attendance a WHERE a.user.id = :userId AND a.date = :todayDate")
	LocalTime findInTimeByUserIdAndDate1(@Param("userId") long userId, @Param("todayDate") LocalDate todayDate);

	Attendance findById(long id);

}
