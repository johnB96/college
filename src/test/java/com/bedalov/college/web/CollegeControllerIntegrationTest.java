package com.bedalov.college.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CollegeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getForEntity_givenNoCollegeParameter() {
        ResponseEntity<String> response = restTemplate.getForEntity(CollegeController.COST_ROUTE, String.class);

        assertEquals(response.getStatusCodeValue(), 400);
    }

    @Test
    public void getForEntity_givenGivenValidCollege_thenResultsMatch() {
        ResponseEntity<String> response = restTemplate
                .getForEntity(CollegeController.COST_ROUTE + "?college=Adelphi University", String.class);

        assertEquals(getResult("Adelphi University", 54184.0, true),
                response.getBody());
    }

    @Test
    public void getForEntity_givenGivenValidCollegeNoBoard_thenResultsMatch() {
        ResponseEntity<String> response = restTemplate
                .getForEntity(CollegeController.COST_ROUTE +
                        "?college=Adelphi University&boarding=false", String.class);

        assertEquals(getResult("Adelphi University", 38657, false),
                response.getBody());
    }

    @Test
    public void getForEntity_givenGivenValidCollegeAndBoarding_thenResultsMatch() {
        ResponseEntity<String> response = restTemplate
                .getForEntity(CollegeController.COST_ROUTE +
                        "?college=Adelphi University&boarding=true", String.class);

        assertEquals(getResult("Adelphi University", 54184.0, true),
                response.getBody());
    }

    private String getResult(String college, double cost, boolean boarding) {
        return String.format("{\"college\":\"%s\",\"cost\":\"$%.2f\",\"boarding\":%b}", college, cost, boarding);
    }
}
