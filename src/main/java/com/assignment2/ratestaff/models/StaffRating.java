package com.assignment2.ratestaff.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name="staff_ratings")
public class StaffRating {
    // Auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private RoleType roleType;
    private int clarity;
    private int niceness;
    private int knowledgeableScore;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StaffRating(String name, String email, RoleType roleType, int clarity, int niceness, int knowledgeableScore,
            String comment) {
        this.name = name;
        this.email = email;
        this.roleType = roleType;
        this.clarity = clarity;
        this.niceness = niceness;
        this.knowledgeableScore = knowledgeableScore;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // dummy

    public StaffRating(){
        name = "John Doe";
        email = "test@email.com";
        roleType = RoleType.PROF;
        clarity = 10;
        knowledgeableScore = 0;
        comment = "Dummy Rating Used for Testing Purposes";
    }
    

    //Setters & Getters

    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public RoleType getRoleType() {
        return roleType;
    }
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    public int getClarity() {
        return clarity;
    }
    public void setClarity(int clarity) {
        this.clarity = clarity;
    }
    public int getKnowledgeableScore() {
        return knowledgeableScore;
    }
    public void setKnowledgeableScore(int knowledgeableScore) {
        this.knowledgeableScore = knowledgeableScore;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public int getNiceness() {
        return niceness;
    }

    public void setNiceness(int niceness) {
        this.niceness = niceness;
    }

    
    //might not need this
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    

    

}
