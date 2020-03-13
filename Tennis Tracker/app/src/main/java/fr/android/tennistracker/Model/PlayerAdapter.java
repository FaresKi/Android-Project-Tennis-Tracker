package fr.android.tennistracker.Model;

import android.content.Context;

import java.util.ArrayList;

public class PlayerAdapter {
    
    private Context context;
    
    public static ArrayList<Player> playerList;

    public PlayerAdapter(Context context, ArrayList<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }
}
