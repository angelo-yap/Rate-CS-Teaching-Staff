package com.assignment2.ratestaff.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<StaffRating,Long>{
    List<StaffRating> findByName(String name);    
}
