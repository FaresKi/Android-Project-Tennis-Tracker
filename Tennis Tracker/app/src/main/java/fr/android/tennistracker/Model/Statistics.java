package fr.android.tennistracker.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistics implements Parcelable {
    public static final Creator<Statistics> CREATOR = new Creator<Statistics>() {
        @Override
        public Statistics createFromParcel(Parcel in) {
            return new Statistics(in);
        }

        @Override
        public Statistics[] newArray(int size) {
            return new Statistics[size];
        }
    };


    private transient static final AtomicInteger count = new AtomicInteger(0);
    private int statsId = 0;
    private int playerId;
    private int firstServes = 0;
    private int aces = 0;
    private int doubleFaults = 0;
    private int pointsWonOnFirstServe = 0;
    private int winners = 0;
    private int unforcedErrors = 0;
    private int forcedErrors = 0;

    public void setStatsId(int statsId) {
        this.statsId = statsId;
    }

    public Statistics() {
        statsId = count.incrementAndGet();
    }

    public Statistics(int firstServes, int aces, int doubleFaults, int pointsWonOnFirstServe, int winners, int unforcedErrors, int forcedErrors) {
        this.firstServes = firstServes;
        this.aces = aces;
        this.doubleFaults = doubleFaults;
        this.pointsWonOnFirstServe = pointsWonOnFirstServe;
        this.winners = winners;
        this.unforcedErrors = unforcedErrors;
        this.forcedErrors = forcedErrors;
        
    }

    protected Statistics(Parcel in) {
        statsId = in.readInt();
        playerId = in.readInt();
        firstServes = in.readInt();
        aces = in.readInt();
        doubleFaults = in.readInt();
        pointsWonOnFirstServe = in.readInt();
        winners = in.readInt();
        unforcedErrors = in.readInt();
        forcedErrors = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(playerId);
        dest.writeInt(statsId);
        dest.writeInt(firstServes);
        dest.writeInt(aces);
        dest.writeInt(doubleFaults);
        dest.writeInt(pointsWonOnFirstServe);
        dest.writeInt(winners);
        dest.writeInt(unforcedErrors);
        dest.writeInt(forcedErrors);
    }

    public int getStatsId() {
        return statsId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "statsId=" + statsId +
                ", playerId=" + playerId +
                ", firstServes=" + firstServes +
                ", aces=" + aces +
                ", doubleFaults=" + doubleFaults +
                ", pointsWonOnFirstServe=" + pointsWonOnFirstServe +
                ", winners=" + winners +
                ", unforcedErrors=" + unforcedErrors +
                ", forcedErrors=" + forcedErrors +
                '}';
    }
}
