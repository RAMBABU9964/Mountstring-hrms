package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class FixedDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fixed_intime")
    private LocalTime fixedinTime = LocalTime.of(9, 0);

    private LocalTime fixedOutTime = LocalTime.of(18, 0);

    private double fixedworkingHrs = 9.0;

    public FixedDetails(Long id, LocalTime fixedinTime, LocalTime fixedOutTime, double fixedworkingHrs, LocalDate createdAt) {
        this.id = id;
        this.fixedinTime = fixedinTime;
        this.fixedOutTime = fixedOutTime;
        this.fixedworkingHrs = fixedworkingHrs;
        this.createdAt = createdAt;
    }

    public FixedDetails() {
    }

    @Column(name = "created_at")
    private LocalDate createdAt; // Adding createdAt field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getFixedinTime() {
        return fixedinTime;
    }

    public void setFixedinTime(LocalTime fixedinTime) {
        this.fixedinTime = fixedinTime;
    }

    public LocalTime getFixedOutTime() {
        return fixedOutTime;
    }

    public void setFixedOutTime(LocalTime fixedOutTime) {
        this.fixedOutTime = fixedOutTime;
    }

    public double getFixedworkingHrs() {
        return fixedworkingHrs;
    }

    public void setFixedworkingHrs(double fixedworkingHrs) {
        this.fixedworkingHrs = fixedworkingHrs;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
// Getters and setters for other fields...


}
