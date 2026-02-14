# TESTING

This project uses slice tests as they only load specific parts of Spring needed for the feature being tested,
which keeps the tests quick and easy to debug.

To run tests use
```bash
./mvnw test
```
or a specific test
```bash
./mvnw -Dtest=TestName test
```

## Validation Tests (Bean Validation)

- Tests server side validation for the StaffRating model using a Validator:
- Cases:
    - Invalid email
    - Missing name
    - Missing email
    - Out-of-range score
    - Optional comment

## Web / Controller Tests (MockMvc)

- Uses mock repository to run tests so database is unaffected
- Cases:
    - GET index returns OK (200) and contains ratings (all staffRatings in the repository) in the model
    - POST success redirects to index properly
    - POST failure returns the form with field errors
    - POST with duplicate email returns the form with duplicate email error message
    - GET details for given ID returns OK (200) when rating exists
    - POST delete sucessfully deletes StaffRating and redirects to index properly
    - POST delete failed returns 404 (when rating does not exist)

## Persistence tests (DataJPATest)

- Tests JPA persistence with in-memory database
- Cases:
    - Saving a StaffRating and retrieving from the database is successful
    - Deleting a StaffRating removes the StaffRating from the database
    - Saving a StaffRating with a duplicate email is rejected by the database
