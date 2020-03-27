package fr.android.tennistracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Set implements Parcelable {
   private int setNumber;
   private List<Statistics> playersStats;

    public Set(int setNumber) {
        this.setNumber = setNumber;
    }

    protected Set(Parcel in) {
        setNumber = in.readInt();
        playersStats = in.createTypedArrayList(Statistics.CREATOR);
    }

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

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public List<Statistics> getFirstPlayers() {
        return playersStats;
    }

    public void setPlayersStats(List<Statistics> firstPlayers) {
        this.playersStats = firstPlayers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(setNumber);
        dest.writeTypedList(playersStats);
    }
}
