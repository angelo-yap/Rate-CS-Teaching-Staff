package com.assignment2.ratestaff.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Persistence tests for StaffRatingRepository.
 */
@DataJpaTest
class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository ratingRepo;

    /**
     * Ensures that saving a StaffRating and retrieving it by id works.
     */
    @Test
    void saveAndRetrieveWorks() {
        StaffRating saved = ratingRepo.save(makeRating("A", "a@sfu.ca"));

        StaffRating found = ratingRepo.findById(saved.getId()).orElseThrow();
        assertEquals("A", found.getName());
        assertEquals("a@sfu.ca", found.getEmail());
    }

    /**
     * Ensures that deleting a StaffRating removes it from the database.
     */
    @Test
    void deleteRemovesEntry() {
        StaffRating saved = ratingRepo.save(makeRating("B", "b@sfu.ca"));

        ratingRepo.deleteById(saved.getId());

        assertTrue(ratingRepo.findById(saved.getId()).isEmpty());
    }

    /**
     * Ensures that duplicate emails are rejected by the database unique constraint.
     */
    @Test
    void duplicateEmailRejectedByDb() {
        ratingRepo.saveAndFlush(makeRating("C", "c@sfu.ca"));

        try {
            ratingRepo.saveAndFlush(makeRating("Other", "c@sfu.ca"));
            fail();
        } catch (DataIntegrityViolationException e) {
            // Reaching here confirms uniqueness is enforced.
            assertEquals(DataIntegrityViolationException.class, e.getClass());
        }
    }

    /**
     * Helper to create a valid StaffRating for repository tests.
     */
    private StaffRating makeRating(String name, String email) {
        StaffRating r = new StaffRating();
        r.setName(name);
        r.setEmail(email);
        r.setRoleType(RoleType.TA);
        r.setClarity(5);
        r.setNiceness(5);
        r.setKnowledgeableScore(5);
        r.setComment("");
        return r;
    }
}
