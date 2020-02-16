package com.bedalov.college.web;

import com.bedalov.college.datastore.CollegeEntity;

public class CollegeResponse {

    private final String college;
    private final String cost;
    private final boolean boarding;

    static CollegeResponse getInstance(CollegeEntity collegeEntity, boolean boarding) {
        double cost = collegeEntity.getInState();
        if (boarding) {
            cost += collegeEntity.getRoomAndBoard();
        }
        return new CollegeResponse(collegeEntity.getCollegeName(), getCost(cost), boarding);
    }

    private static String getCost(double number) {
        return String.format("$%.2f", number);
    }

    private CollegeResponse(String college, String cost, boolean boarding) {
        this.college = college;
        this.cost = cost;
        this.boarding = boarding;
    }

    public String getCollege() {
        return college;
    }

    public String getCost() {
        return cost;
    }

    public boolean getBoarding() {
        return boarding;
    }
}
