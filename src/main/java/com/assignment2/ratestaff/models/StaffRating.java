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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
@Table(name="staff_ratings")
public class StaffRating {
    // Auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     *  Name of staff member being rated. Cannot be blank and must be 80 characters or less.
     */
    @NotNull(message = "Name must not be blank.")
    @NotBlank(message = "Name must not be blank.")
    @Size(max = 80, message = "Name must be 80 characters or less.")
    @Column(nullable = false)
    private String name;

    /**
     *  Email of staff member being rated. Cannot be blank, must be a valid email format and must be unique in the database.
     */
    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid (e.g. name@sfu.ca)")
    @NotBlank(message = "Email must not be blank.")
    @Column(nullable = false, unique = true)
    private String email;
    
    /**
     *  Role type of staff member being rated. Cannot be null and must be one of the values defined in the RoleType enum (TA, PROF, INSTRUCTOR, STAFF).
     */
    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;
    
    /**
     *  Clarity rating of staff member being rated. Cannot be null and must be an integer between 1 and 10 inclusive.
     */
    @NotNull(message = "Clarity is required")
    @Min(value = 1, message = "Clarity must be between 1 and 10")
    @Max(value = 10, message = "Clarity must be between 1 and 10")
    @Column(nullable = false)
    private int clarity;

    /**
     *  Niceness rating of staff member being rated. Cannot be null and must be an integer between 1 and 10 inclusive.
     */
    @NotNull(message = "Niceness is required")
    @Min(value = 1, message = "Niceness must be between 1 and 10")
    @Max(value = 10, message = "Niceness must be between 1 and 10")
    @Column(nullable = false)
    private int niceness;

    /**
     * Knowledgeable score rating of staff member being rated. Cannot be null and must be an integer between 1 and 10 inclusive.
     */
    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be between 1 and 10")
    @Max(value = 10, message = "Knowledgeable score must be between 1 and 10")
    @Column(nullable = false)
    private int knowledgeableScore;

    /**
     *  Optional comment about the staff member being rated. Must be 500 characters or less.
     */
    private String comment;
    
    /**
     *  Timestamps for when the rating was created and last updated. Automatically set when the rating is created or updated in the database.
     */
    private LocalDateTime createdAt;

    /**
     *  Timestamp for when the rating was last updated. Automatically set when the rating is updated in the database.
     */
    private LocalDateTime updatedAt;

    /**
     *  No-argument constructor required by JPA. Initializes createdAt and updatedAt to the current time.
     */
    public StaffRating(){}
    

    //Setters & Getters

    public void setId(Long id) {
        this.id = id;
    }
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
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public int getNiceness() {
        return niceness;
    }
    public void setNiceness(int niceness) {
        this.niceness = niceness;
    }
    
    /**
     *  Returns a string to display as the title of the staff member being rated in the details page. 
     *  Title is determined by role type and generated using displayTitle() method of corresponding class.
     * @return String title to display for staff member.
     */
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
    
    /**
     *  Calculates and returns the overall score for the staff member being rated, which is the average of the clarity, niceness and knowledgeable score ratings.
     * @return Double overall score for staff member being rated.
     */
    @Transient
    public double getOverallScore() {
        return (clarity + niceness + knowledgeableScore) / 3.0;
    }

    /**
     *  Sets createdAt and updatedAt to the current date and time when rating is created.
     */
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
    
    /**
     *  Sets updatedAt to the current date and time when rating is updated.
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
