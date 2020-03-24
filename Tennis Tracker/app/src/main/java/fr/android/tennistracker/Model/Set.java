package fr.android.tennistracker.Model;

import java.util.List;

public class Set {
   private int setNumber;
   private List<Statistics> playersStats;

    public Set(int setNumber) {
        this.setNumber = setNumber;
    }

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
}
