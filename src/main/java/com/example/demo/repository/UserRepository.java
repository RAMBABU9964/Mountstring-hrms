package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
 
	User findByEmail(String email);
	public User findOneById(Long id);
	
	
	@Query("SELECT u FROM User u WHERE u NOT IN (:teamUsers)")
    List<User> findAllByNotInTeam(@Param("teamUsers") List<User> teamUsers);

	
}
