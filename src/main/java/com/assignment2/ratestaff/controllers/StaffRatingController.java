package com.assignment2.ratestaff.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.assignment2.ratestaff.models.StaffRating;
import com.assignment2.ratestaff.models.RoleType;
import com.assignment2.ratestaff.models.StaffMemberProfile;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StaffRatingController{

    @Autowired
    private StaffMemberProfile ratingRepo;

    @GetMapping("/ratings/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        
        List<StaffRating> staffRatings = ratingRepo.findAll();    
        // end of database call 
         
        model.addAttribute("rt", staffRatings);
        return "staffrating/showAll";
    }
    
    @PostMapping("/ratings/add")
    public String addRating(@RequestParam Map<String, String> newrating, HttpServletResponse response) {
        System.out.println("ADD Rating");
        String newName = newrating.get("name");
        String newEmail = newrating.get("email");
        RoleType newRoleType = RoleType.valueOf(newrating.get("roletype"));
        int newClarity = Integer.parseInt(newrating.get("clarity"));
        int newNiceness = Integer.parseInt(newrating.get("niceness"));
        int newKnowledgeableScore = Integer.parseInt(newrating.get("knowledgeable-score"));

        ratingRepo.save(new StaffRating(newName, newEmail, newRoleType, newClarity, newNiceness, newKnowledgeableScore, "test"));
        response.setStatus(201);
        return "staffrating/addedRating";
    }
    
}