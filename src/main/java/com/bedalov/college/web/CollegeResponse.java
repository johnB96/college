package com.bedalov.college.web;

import com.bedalov.college.datastore.CollegeEntity;

public class CollegeResponse {

    private final String college;
    private final double cost;
    private final boolean boarding;

    static CollegeResponse getInstance(CollegeEntity collegeEntity, boolean boarding) {
        double cost = collegeEntity.getInState();
        if (boarding) {
            cost += collegeEntity.getRoomAndBoard();
        }
        return new CollegeResponse(collegeEntity.getCollegeName(), cost, boarding);
    }

    private CollegeResponse(String college, double cost, boolean boarding) {
        this.college = college;
        this.cost = cost;
        this.boarding = boarding;
    }

    public String getCollege() {
        return college;
    }

    public double getCost() {
        return cost;
    }

    public boolean getBoarding() {
        return boarding;
    }
}
