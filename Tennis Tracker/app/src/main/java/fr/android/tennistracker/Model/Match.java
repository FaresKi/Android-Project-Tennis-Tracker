package fr.android.tennistracker.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Match implements Parcelable {
    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    private static final AtomicInteger count = new AtomicInteger(0);
    private  int id = 0;
    private  Player playerOne, playerTwo;
    private transient List<Statistics> playersStats;

    public Match(Player playerOne, Player playerTwo) {
        id = count.incrementAndGet();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        playersStats = Arrays.asList(playerOne.getPlayerStats(), playerTwo.getPlayerStats());
    }

    public Match(String firstPlayer, String secondPlayer) {
        this.playerOne = new Player(firstPlayer);
        this.playerTwo = new Player(secondPlayer);
        playersStats = Arrays.asList(playerOne.getPlayerStats(), playerTwo.getPlayerStats());
    }

    protected Match(Parcel in) {
        id = in.readInt();
        playerOne = in.readParcelable(Player.class.getClassLoader());
        playerTwo = in.readParcelable(Player.class.getClassLoader());
        playersStats = in.createTypedArrayList(Statistics.CREATOR);
    }

    public int getId() {
        return id;
    }


    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public List<Statistics> getPlayersStats() {
        return playersStats;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", playerOne=" + playerOne.getName() +
                ", playerTwo=" + playerTwo.getName() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(playerOne, flags);
        dest.writeParcelable(playerTwo, flags);
        dest.writeTypedList(playersStats);
    }
}
