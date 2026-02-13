package com.assignment2.ratestaff.models;

import java.time.LocalDateTime;

import com.assignment2.ratestaff.design.InstructorProfile;
import com.assignment2.ratestaff.design.ProfProfile;
import com.assignment2.ratestaff.design.StaffMemberProfile;
import com.assignment2.ratestaff.design.StaffProfile;
import com.assignment2.ratestaff.design.TaProfile;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
@Table(name="staff_ratings")
public class StaffRating {
    // Auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Name is required")
    @Size(min = 1, max = 80, message = "Name must be at most")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid (e.g. name@sfu.ca)")
    @Size(min = 1, message = "Email must not be empty")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;
    
    @NotNull(message = "Clarity is required")
    @Min(value = 1, message = "Clarity must be between 1 and 10")
    @Max(value = 10, message = "Clarity must be between 1 and 10")
    @Column(nullable = false)
    private int clarity;

    @NotNull(message = "Niceness is required")
    @Min(value = 1, message = "Niceness must be between 1 and 10")
    @Max(value = 10, message = "Niceness must be between 1 and 10")
    @Column(nullable = false)
    private int niceness;

    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be between 1 and 10")
    @Max(value = 10, message = "Knowledgeable score must be between 1 and 10")
    @Column(nullable = false)
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

    public StaffRating(){}
    

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
    
    @Transient
    public String getDisplayTitle() {
        StaffMemberProfile p = switch (roleType) {
            case TA -> new TaProfile();
            case PROF -> new ProfProfile();
            case INSTRUCTOR -> new InstructorProfile();
            case STAFF -> new StaffProfile();
        };
        return p.displayTitle();
    }
    
    @Transient
    public double getOverallScore() {
        return (clarity + niceness + knowledgeableScore) / 3.0;
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
    
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
