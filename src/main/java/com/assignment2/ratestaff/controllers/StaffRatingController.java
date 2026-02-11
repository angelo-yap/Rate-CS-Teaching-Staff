package com.assignment2.ratestaff.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.assignment2.ratestaff.models.StaffRating;

@Controller
public class StaffRatingController{
    @GetMapping("/staffrating/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        
        // TODO: get all users from database
        
        List<StaffRating> ratings = new ArrayList<>();
        ratings.add(new StaffRating()); 
        ratings.add(new StaffRating());
        ratings.add(new StaffRating());
        // end of database call 
         
        model.addAttribute("rt", ratings);
        return "staffrating/showAll";
    }
}
