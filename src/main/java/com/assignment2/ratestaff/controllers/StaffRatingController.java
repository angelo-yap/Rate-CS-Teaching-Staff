package com.assignment2.ratestaff.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment2.ratestaff.models.StaffRating;
import com.assignment2.ratestaff.models.RatingsRepository;
import com.assignment2.ratestaff.models.RoleType;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;



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
    
    @PostMapping("/ratings/add")
    public String postMethodName(@Valid @ModelAttribute("staffRating") StaffRating staffRating, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            return "staffrating/create";
        }
        
        ratingRepo.save(staffRating);
        return "redirect:/ratings";
    }
    
    @GetMapping("/ratings/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        System.out.println("Showing Rating Details");
        StaffRating rating = ratingRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found"));
        model.addAttribute("rating",rating);
        return "staffrating/details";
    }
}