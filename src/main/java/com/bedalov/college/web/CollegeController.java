package com.bedalov.college.web;

import com.bedalov.college.datastore.CollegeEntity;
import com.bedalov.college.datastore.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Validated
public class CollegeController {

    static final String COST_ROUTE = "/v1/cost";
    static final String BOARDING_PARAM = "boarding";
    static final String COLLEGE_PARAM = "college";
    static final String NOT_FOUND_MESSAGE = "Error: College not found";
    static final String BAD_REQUEST_MESSAGE = "Error: College name is required";

    private CollegeRepository repository;

    @Autowired
    public CollegeController(CollegeRepository repository) {
        this.repository = repository;
    }

    @GetMapping(COST_ROUTE)
    public ResponseEntity getCost(
            @RequestParam(value = COLLEGE_PARAM, required = false) String college,
            @RequestParam(value = BOARDING_PARAM, required = false) Boolean boarding) {
        if (StringUtils.isEmpty(college)) {
            return ResponseEntity.status(400).body(BAD_REQUEST_MESSAGE);
        }
        CollegeEntity entity = repository.getEntity(college);
        if (entity == null) {
            return ResponseEntity.status(404).body(NOT_FOUND_MESSAGE);
        }
        return ResponseEntity.ok(CollegeResponse.getInstance(entity, Objects.requireNonNullElse(boarding, true)));
    }
}
