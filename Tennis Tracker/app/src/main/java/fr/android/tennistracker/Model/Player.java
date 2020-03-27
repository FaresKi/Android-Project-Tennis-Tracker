package fr.android.tennistracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    public String name;
    private int sets;
    private Statistics playerStats = new Statistics();

    /**
     * Constructor for a Player.
     * Initializes a player to the given name
     * and zero as the number of sets won.
     *
     * @param name Name of the player
     */
    public Player(String name) {
        sets = 0;
        this.name = name;
    }

    /**
     * @return Number of sets won
     */
    public int getSet() {
        return sets;
    }

    /**
     * @return Name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Increments the number of sets won by 1
     */
    public void winSet() {
        sets++;
    }

    public Statistics getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(Statistics playerStats) {
        this.playerStats = playerStats;
    }

    public void reinitialiseStats() {
        this.playerStats = new Statistics();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
