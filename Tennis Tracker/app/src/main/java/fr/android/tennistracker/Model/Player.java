package fr.android.tennistracker.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

import java.util.concurrent.atomic.AtomicInteger;

public class Player implements Parcelable {

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    private transient static final AtomicInteger count = new AtomicInteger(0);
    
    @Expose
    private int playerId;
    
    private  String name;
    
    private transient int sets;
    private transient Statistics playerStats = new Statistics();


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
        this.playerId = count.incrementAndGet();
        playerStats.setPlayerId(playerId);
    }

    protected Player(Parcel in) {
        playerId = in.readInt();
        name = in.readString();
        sets = in.readInt();
        playerStats = in.readParcelable(Statistics.class.getClassLoader());
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


    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(playerId);
        dest.writeString(name);
        dest.writeInt(sets);
        dest.writeParcelable(playerStats, flags);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                '}';
    }
}
