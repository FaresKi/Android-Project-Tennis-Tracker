package fr.android.tennistracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Set implements Parcelable {
    public static final Creator<Set> CREATOR = new Creator<Set>() {
        @Override
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        @Override
        public Set[] newArray(int size) {
            return new Set[size];
        }
    };
    private int setNumber;
    private String scoreFP;
    private String scoreSP;
    private String setScoreFirstPlayer;
    private String setScoreSecondPlayer;
    private List<Statistics> playersStats;

    protected Set(Parcel in) {
        setNumber = in.readInt();
        scoreFP = in.readString();
        scoreSP = in.readString();
        setScoreFirstPlayer = in.readString();
        setScoreSecondPlayer = in.readString();
        playersStats = in.createTypedArrayList(Statistics.CREATOR);
    }

    public Set(int setNumber) {
        this.setNumber = setNumber;
    }

    public CharSequence getSetScoreFirstPlayer() {
        return setScoreFirstPlayer;
    }

    public void setSetScoreFirstPlayer(String setScoreFirstPlayer) {
        this.setScoreFirstPlayer = setScoreFirstPlayer;
    }

    public CharSequence getSetScoreSecondPlayer() {
        return setScoreSecondPlayer;
    }

    public void setSetScoreSecondPlayer(String setScoreSecondPlayer) {
        this.setScoreSecondPlayer = setScoreSecondPlayer;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public List<Statistics> getPlayersStats() {
        return playersStats;
    }

    public void setPlayersStats(List<Statistics> firstPlayers) {
        this.playersStats = firstPlayers;
    }


    public CharSequence getScoreFP() {
        return scoreFP;
    }

    public void setScoreFP(String scoreFP) {
        this.scoreFP = scoreFP;
    }

    public CharSequence getScoreSP() {
        return scoreSP;
    }

    public void setScoreSP(String scoreSP) {
        this.scoreSP = scoreSP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(setNumber);
        dest.writeString(scoreFP);
        dest.writeString(scoreSP);
        dest.writeString(setScoreFirstPlayer);
        dest.writeString(setScoreSecondPlayer);
        dest.writeTypedList(playersStats);
    }
}
