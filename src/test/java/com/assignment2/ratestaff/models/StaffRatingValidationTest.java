package com.assignment2.ratestaff.models;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 *  Validation tests for  
 */
class StaffRatingValidationTest {

    private static Validator validator;

    /**
     *  Sets up the validator instance before all tests are run.
     */
    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     *  Ensures that an invalid email format is rejected by validation.
     */
    @Test
    void invalidEmailRejected() {
        StaffRating r = validRating();
        r.setEmail("notanemail");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);

        boolean hasEmailError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));

        if (!hasEmailError) {
            fail("Expected email validation error, but got: " + violations);
        }
    }

    /**
     *  Ensures that a missing email is rejected by validation.
     */
    @Test
    void missingEmailRejected() {
        StaffRating r = validRating();
        r.setEmail("   "); // whitespace should fail @NotBlank

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);

        boolean hasEmailError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));

        if (!hasEmailError) {
            fail("Expected email required validation error, but got: " + violations);
        }
    }

    /**
     *  Ensures that a missing or whitespace-only name is rejected by validation.
     */
    @Test
    void missingNameRejected() {
        StaffRating r = validRating();
        r.setName("   "); // whitespace should fail @NotBlank

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);

        boolean hasNameError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));

        if (!hasNameError) {
            fail("Expected name validation error, but got: " + violations);
        }
    }

    /**
     *  Ensures that out-of-range scores are rejected by validation.
     */
    @Test
    void outOfRangeScoreRejected() {
        StaffRating r = validRating();
        r.setClarity(11);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);

        boolean hasClarityError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("clarity"));

        if (!hasClarityError) {
            fail("Expected clarity validation error, but got: " + violations);
        }
    }

    /**
     *  Ensures that blankspace-only comments are allowed (optional field).
     *  Remove this test if you decided comment must not be blank.
     */
    @Test
    void optionalCommentAllowed() {
        StaffRating r = validRating();
        r.setComment("   "); // should be allowed if comment is optional

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);

        boolean hasCommentError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("comment"));

        if (hasCommentError) {
            fail("Expected comment to be optional, but got: " + violations);
        }
    }

    /**
     * Helper to create a valid StaffRating for test.
     */
    private StaffRating validRating() {
        StaffRating r = new StaffRating();
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
