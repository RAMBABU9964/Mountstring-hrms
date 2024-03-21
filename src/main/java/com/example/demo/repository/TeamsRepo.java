package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Teams;

public interface TeamsRepo extends JpaRepository<Teams, Long> {

}
