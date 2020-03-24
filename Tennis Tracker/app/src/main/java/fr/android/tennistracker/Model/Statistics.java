package fr.android.tennistracker.Model;

public class Statistics {
    private int wonRallies = 0;
    private int firstServes = 0;
    private int aces = 0;
    private int doubleFaults = 0;
    private int pointsWonOnFirstServe = 0;
    
    private int winners = 0;
    private int unforcedErrors = 0;
    private int forcedErrors = 0;

    public int getWonRallies() {
        return wonRallies;
    }

    public void setWonRallies(int wonRallies) {
        this.wonRallies = wonRallies;
    }

    public int getFirstServes() {
        return firstServes;
    }

    public void setFirstServes(int firstServes) {
        this.firstServes = firstServes;
    }

    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    public int getDoubleFaults() {
        return doubleFaults;
    }

    public void setDoubleFaults(int doubleFaults) {
        this.doubleFaults = doubleFaults;
    }

    public int getPointsWonOnFirstServe() {
        return pointsWonOnFirstServe;
    }

    public void setPointsWonOnFirstServe(int pointsWonOnFirstServe) {
        this.pointsWonOnFirstServe = pointsWonOnFirstServe;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public int getUnforcedErrors() {
        return unforcedErrors;
    }

    public void setUnforcedErrors(int unforcedErrors) {
        this.unforcedErrors = unforcedErrors;
    }

    public int getForcedErrors() {
        return forcedErrors;
    }

    public void setForcedErrors(int forcedErrors) {
        this.forcedErrors = forcedErrors;
    }
}
