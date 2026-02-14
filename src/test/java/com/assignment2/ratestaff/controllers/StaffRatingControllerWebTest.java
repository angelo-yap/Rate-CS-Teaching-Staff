package com.assignment2.ratestaff.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment2.ratestaff.models.RoleType;
import com.assignment2.ratestaff.models.StaffRating;
import com.assignment2.ratestaff.models.StaffRatingRepository;

/**
 * Web layer tests for StaffRatingController.
 */
@WebMvcTest(StaffRatingController.class)
class StaffRatingControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffRatingRepository ratingRepo;

    /**
     * Ensures that GET /ratings returns HTTP 200 and includes ratings in the model.
     */
    @Test
    void getIndexReturns200() throws Exception {
        when(ratingRepo.findAll()).thenReturn(List.of(sampleRating(1L)));

        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"))
                .andExpect(view().name("staffrating/index"));
    }

    /**
     * Ensures that POST /ratings/add with valid input redirects to the index page.
     */
    @Test
    void postCreateSuccessRedirects() throws Exception {
        when(ratingRepo.save(any(StaffRating.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(post("/ratings/add")
                        .param("name", "John Doe")
                        .param("email", "johnd@sfu.ca")
                        .param("roleType", "TA")
                        .param("clarity", "5")
                        .param("niceness", "6")
                        .param("knowledgeableScore", "7")
                        .param("comment", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));

        verify(ratingRepo).save(any(StaffRating.class));
    }

    /**
     * Ensures that POST /ratings/add with invalid input returns the form with field errors.
     * Also ensures the program does not crash and does not save invalid data.
     */
    @Test
    void postCreateFailureReturnsFormWithErrors() throws Exception {
        mockMvc.perform(post("/ratings/add")
                        .param("name", "")
                        .param("email", "bad")
                        .param("roleType", "TA")
                        .param("clarity", "99")
                        .param("niceness", "6")
                        .param("knowledgeableScore", "7")
                        .param("comment", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("staffRating", "name", "email", "clarity"))
                .andExpect(view().name("staffrating/create"));

        verify(ratingRepo, never()).save(any(StaffRating.class));
    }

    /**
     * Ensures that duplicate email errors are handled gracefully (no crash).
     */
    @Test
    void postCreateDuplicateEmailShowsEmailError() throws Exception {
        when(ratingRepo.save(any(StaffRating.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        mockMvc.perform(post("/ratings/add")
                        .param("name", "John Doe")
                        .param("email", "johnd@sfu.ca")
                        .param("roleType", "TA")
                        .param("clarity", "5")
                        .param("niceness", "6")
                        .param("knowledgeableScore", "7")
                        .param("comment", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("staffRating", "email"))
                .andExpect(view().name("staffrating/create"));
    }

    /**
     * Ensures that GET /ratings/{id} returns HTTP 200 when the rating exists.
     */
    @Test
    void getDetailsReturns200() throws Exception {
        when(ratingRepo.findById(1L)).thenReturn(Optional.of(sampleRating(1L)));

        mockMvc.perform(get("/ratings/1"))
                .andExpect(status().isOk()).andExpect(model().attributeExists("staffRating"))
                .andExpect(view().name("staffrating/details"));
    }

    /**
     * Ensures that POST /ratings/{id}/delete deletes an existing rating and redirects to index.
     */
    @Test
    void postDeleteExistingRedirects() throws Exception {
        when(ratingRepo.existsById(1L)).thenReturn(true);

        mockMvc.perform(post("/ratings/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));

        verify(ratingRepo).deleteById(1L);
    }

    /**
     * Ensures that POST /ratings/{id}/delete returns 404 when the rating does not exist.
     */
    @Test
    void postDeleteMissingReturns404() throws Exception {
        when(ratingRepo.existsById(999L)).thenReturn(false);

        mockMvc.perform(post("/ratings/999/delete"))
                .andExpect(status().isNotFound());
    }

    /**
     * Helper to create a StaffRating used by web tests.
     */
    private StaffRating sampleRating(Long id) {
        StaffRating r = new StaffRating();
        r.setId(id);
        r.setName("John Doe");
        r.setEmail("johnd@sfu.ca");
        r.setRoleType(RoleType.TA);
        r.setClarity(5);
        r.setNiceness(6);
        r.setKnowledgeableScore(7);
        r.setComment("");
        return r;
    }
}
