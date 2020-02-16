package com.bedalov.college.datastore;

public class CollegeEntity {

    private final String collegeName;
    private final double inState;
    private final double outOfState;
    private final double roomAndBoard;

    public CollegeEntity(
            String collegeName,
            double inState,
            double outOfState,
            double roomAndBoard) {
        this.collegeName = collegeName;
        this.inState = inState;
        this.outOfState = outOfState;
        this.roomAndBoard = roomAndBoard;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public double getInState() {
        return inState;
    }

    public double getOutOfState() {
        return outOfState;
    }

    public double getRoomAndBoard() {
        return roomAndBoard;
    }
}
