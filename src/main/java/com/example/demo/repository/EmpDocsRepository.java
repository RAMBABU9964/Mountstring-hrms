package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmpDocs;
@Repository
public interface EmpDocsRepository extends JpaRepository<EmpDocs, Long> {

	@Query("SELECT b FROM EmpDocs b WHERE b.user.id = :userId")
	EmpDocs findByUserId(Long userId);

}
