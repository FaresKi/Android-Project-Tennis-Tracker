package fr.android.tennistracker.Model;

public class Match {
    private int id;
    private String accountID;
    private String firstPlayer;
    private String secondPlayer;
    public Match(String accountID, String firstPlayer, String secondPlayer) {
        this.accountID = accountID;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
