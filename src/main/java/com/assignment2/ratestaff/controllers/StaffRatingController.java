package com.assignment2.ratestaff.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment2.ratestaff.models.StaffRating;
import com.assignment2.ratestaff.models.RatingsRepository;
import com.assignment2.ratestaff.models.RoleType;
import com.assignment2.ratestaff.models.StaffMemberProfile;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


@Controller
public class StaffRatingController{

    @Autowired
    private RatingsRepository ratingRepo;

    @GetMapping({"/", "/ratings"})
    public String showIndex(Model model){
        System.out.println("Welcome!");
        model.addAttribute("ratings", ratingRepo.findAll());
        return "staffrating/index";
    }

    @GetMapping("/ratings/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        
        List<StaffRating> staffRatings = ratingRepo.findAll();    
        // end of database call 
         
        model.addAttribute("rt", staffRatings);
        return "staffrating/showAll";
    }

    @GetMapping("/ratings/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staffRating", new StaffRating());
        model.addAttribute("roleTypes", RoleType.values());
        return "staffrating/create";
    }
    
    // TODO: MAKE IT NOT STATIC
    @PostMapping("/ratings/add")
    public String addRating(@RequestParam Map<String, String> newrating, HttpServletResponse response) {
        System.out.println("ADD Rating");
        String newName = newrating.get("name");
        String newEmail = newrating.get("email");
        RoleType newRoleType = RoleType.valueOf(newrating.get("roletype"));
        int newClarity = Integer.parseInt(newrating.get("clarity"));
        int newNiceness = Integer.parseInt(newrating.get("niceness"));
        int newKnowledgeableScore = Integer.parseInt(newrating.get("knowledgeable-score"));

        ratingRepo.save(new StaffRating(newName, newEmail, newRoleType, newClarity, newNiceness, newKnowledgeableScore, ""));
        return "ratings/addedRating";
    }
    
    @GetMapping("/ratings/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        System.out.println("Showing Rating Details");
        StaffRating rating = ratingRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found"));
        model.addAttribute("rating",rating);
        return "staffrating/details";
    }
}