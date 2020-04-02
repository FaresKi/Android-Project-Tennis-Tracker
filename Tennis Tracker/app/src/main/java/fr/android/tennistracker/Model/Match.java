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
    @Expose
    private  int matchId;
    @Expose
    private  Player firstPlayer, secondPlayer;
    private transient List<Statistics> playersStats;

    public Match(Player firstPlayer, Player secondPlayer) {
        matchId = count.incrementAndGet();
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        playersStats = Arrays.asList(firstPlayer.getPlayerStats(), secondPlayer.getPlayerStats());
    }
    public Match(Player firstPlayer, Player secondPlayer, int matchId) {
        this.matchId = matchId;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    protected Match(Parcel in) {
        matchId = in.readInt();
        firstPlayer = in.readParcelable(Player.class.getClassLoader());
        secondPlayer = in.readParcelable(Player.class.getClassLoader());
        playersStats = in.createTypedArrayList(Statistics.CREATOR);
    }

    public int getMatchId() {
        return matchId;
    }


    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public List<Statistics> getPlayersStats() {
        return playersStats;
    }

    @Override
    public String toString() {
        return "{" +
                "\"gameId\":"  +  getMatchId()  + 
                ",\"firstPlayer\":" + firstPlayer.getPlayerId() +
                ",\"secondPlayer\":" + secondPlayer.getPlayerId() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(matchId);
        dest.writeParcelable(firstPlayer, flags);
        dest.writeParcelable(secondPlayer, flags);
        dest.writeTypedList(playersStats);
    }
}
