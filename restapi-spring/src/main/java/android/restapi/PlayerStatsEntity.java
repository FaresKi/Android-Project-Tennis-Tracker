package android.restapi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PlayerStats")
public class PlayerStatsEntity {
    private int statsId;
    private int aces;
    private int doubleFaults;
    private Integer firstServes;
    private int forcedErrors;
    private int pointsWonOnFirstServe;
    private int unforcedErrors;
    private int winners;
    private int setNumber;
    private int playerId;

    @Id
    @Column(name = "stats_id")
    public int getStatsId() {
        return statsId;
    }

    public void setStatsId(int statsId) {
        this.statsId = statsId;
    }

    @Basic
    @Column(name = "aces")
    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    @Basic
    @Column(name = "double_faults")
    public int getDoubleFaults() {
        return doubleFaults;
    }

    public void setDoubleFaults(int doubleFaults) {
        this.doubleFaults = doubleFaults;
    }

    @Basic
    @Column(name = "first_serves")
    public Integer getFirstServes() {
        return firstServes;
    }

    public void setFirstServes(Integer firstServes) {
        this.firstServes = firstServes;
    }

    @Basic
    @Column(name = "forced_errors")
    public int getForcedErrors() {
        return forcedErrors;
    }

    public void setForcedErrors(int forcedErrors) {
        this.forcedErrors = forcedErrors;
    }

    @Basic
    @Column(name = "points_won_on_first_serve")
    public int getPointsWonOnFirstServe() {
        return pointsWonOnFirstServe;
    }

    public void setPointsWonOnFirstServe(int pointsWonOnFirstServe) {
        this.pointsWonOnFirstServe = pointsWonOnFirstServe;
    }

    @Basic
    @Column(name = "unforced_errors")
    public int getUnforcedErrors() {
        return unforcedErrors;
    }

    public void setUnforcedErrors(int unforcedErrors) {
        this.unforcedErrors = unforcedErrors;
    }

    @Basic
    @Column(name = "winners")
    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    @Basic
    @Column(name = "set_number")
    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int set) {
        this.setNumber = set;
    }


    @Basic
    @Column(name = "player_id")
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatsEntity that = (PlayerStatsEntity) o;
        return statsId == that.statsId &&
                aces == that.aces &&
                doubleFaults == that.doubleFaults &&
                forcedErrors == that.forcedErrors &&
                pointsWonOnFirstServe == that.pointsWonOnFirstServe &&
                unforcedErrors == that.unforcedErrors &&
                winners == that.winners &&
                setNumber == that.setNumber &&
                playerId == that.playerId &&
                Objects.equals(firstServes, that.firstServes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statsId, aces, doubleFaults, firstServes, forcedErrors, pointsWonOnFirstServe, unforcedErrors, winners, setNumber, playerId);
    }
}
