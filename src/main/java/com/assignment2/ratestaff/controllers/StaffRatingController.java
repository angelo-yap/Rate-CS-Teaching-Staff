package com.assignment2.ratestaff.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment2.ratestaff.models.StaffRating;
import com.assignment2.ratestaff.models.StaffRatingRepository;
import com.assignment2.ratestaff.models.RoleType;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;




@Controller
public class StaffRatingController{

    @Autowired
    private StaffRatingRepository ratingRepo;

    // Index Page
    @GetMapping({"/", "/ratings"})
    public String showIndex(Model model){
        System.out.println("Welcome!");
        model.addAttribute("ratings", ratingRepo.findAll());
        return "staffrating/index";
    }

    // Create Page form
    @GetMapping("/ratings/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staffRating", new StaffRating());
        model.addAttribute("roleTypes", RoleType.values());
        return "staffrating/create";
    }
    
    // Create Page PostMapping
    @PostMapping("/ratings/add")
    public String postMethodName(@Valid @ModelAttribute("staffRating") StaffRating staffRating, BindingResult result, Model model) {

        System.out.println("EMAIL RECEIVED: " + staffRating.getEmail());
        
        if (result.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            return "staffrating/create";
        }
        try{
            ratingRepo.save(staffRating);
            return "redirect:/ratings";
        }catch (DataIntegrityViolationException e){
            result.rejectValue("email", "duplicate", "This email is already in use.");
            model.addAttribute("roleTypes", RoleType.values());
            return "staffrating/create";
        }
    }
    
    // Details Page
    @GetMapping("/ratings/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        System.out.println("Showing Rating Details for id " + id);
        StaffRating rating = ratingRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found"));
        model.addAttribute("staffRating",rating);
        return "staffrating/details";
    }

    // Edit Page
    @GetMapping("/ratings/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        System.out.println("Editing rating details for id " + id);
        StaffRating rating = ratingRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found"));
        model.addAttribute("staffRating",rating);
        model.addAttribute("roleTypes", RoleType.values());
        return "staffrating/edit";
    }

    // Edit Page PostMapping
    @PostMapping("/ratings/{id}/edit")
    public String updateRating(@PathVariable Long id, @Valid @ModelAttribute("staffRating") StaffRating staffRating, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            return "staffrating/edit";
        }

        StaffRating rating = ratingRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found"));

        rating.setName(staffRating.getName());
        rating.setEmail(staffRating.getEmail());
        rating.setRoleType(staffRating.getRoleType());
        rating.setClarity(staffRating.getClarity());
        rating.setNiceness(staffRating.getNiceness());
        rating.setKnowledgeableScore(staffRating.getKnowledgeableScore());
        rating.setComment(staffRating.getComment());

        try {
            ratingRepo.save(rating);
            return "redirect:/ratings/" + id;
        }catch (DataIntegrityViolationException e){
            result.rejectValue("email", "duplicate", "That email already belongs to another user.");
            model.addAttribute("roleTypes", RoleType.values());
            return "staffRating/edit";
        }
    }

    // Delete PostMapping
    @PostMapping("/ratings/{id}/delete")
    public String deleteRating(@PathVariable Long id) {
        
        if (!ratingRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found");
        }
        
        ratingRepo.deleteById(id);;
        return "redirect:/ratings";
    }
    
}