package com.assignment2.ratestaff.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.assignment2.ratestaff.models.StaffRating;
import com.assignment2.ratestaff.models.UserRepository;

@Controller
public class StaffRatingController{

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/staffrating/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        
        List<StaffRating> staffRatings = userRepo.findAll();    
        // end of database call 
         
        model.addAttribute("rt", staffRatings);
        return "staffrating/showAll";
    }
}