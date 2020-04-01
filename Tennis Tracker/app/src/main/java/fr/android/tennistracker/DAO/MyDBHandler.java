package fr.android.tennistracker.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.Model.Statistics;

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String TABLE_NAME_STATS = "Statistics";
    public static final String COLUMN_ID = "stats_id";
    public static final String COLUMN_ACES = "aces";
    public static final String COLUMN_DOUBLE_FAULTS = "double_faults";
    public static final String COLUMN_FIRST_SERVES = "first_serves";
    public static final String COLUMN_FORCED_ERRORS = "forced_errors";
    public static final String COLUMN_POINTS_WON_ON_FIRST_SERVE = "points_won_on_first_serve";
    public static final String COLUMN_UNFORCED_ERROR = "unforced_error";
    public static final String COLUMN_WINNERS = "winners";
    public static final String COLUMN_SET = "set_number";

    public static final String TABLE_NAME_GAME = "Game";
    public static final String COLUMN_PLAYER_ONE = "player_one";
    public static final String COLUMN_PLAYER_TWO = "player_two";
    public static final String COLUMN_ID_MATCH = "matchId";

    public static final String TABLE_NAME_PLAYER = "Player";
    public static final String COLUMN_ID_PLAYER = "playerId";
    public static final String COLUMN_NAME = "name";


    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tennis-tracker.db";
    
    private SQLiteDatabase database;


    public MyDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        
        
        String CREATE_TABLE_PLAYER = "create table if not exists " + TABLE_NAME_PLAYER +
                "(" +
                COLUMN_ID_PLAYER + " int not null primary key," +
                COLUMN_NAME + " text);";
        
        String CREATE_TABLE_GAME = "create table if not exists " + TABLE_NAME_GAME +
                "(\n" +
                COLUMN_ID_MATCH + " int not null primary key," +
                COLUMN_PLAYER_ONE + " int not null," +
                COLUMN_PLAYER_TWO + " int not null," +
                "FOREIGN KEY (" + COLUMN_PLAYER_ONE  + ") REFERENCES " + TABLE_NAME_PLAYER + "(" + COLUMN_ID_PLAYER + ")," +
                "FOREIGN KEY (" + COLUMN_PLAYER_TWO  + ") REFERENCES " + TABLE_NAME_PLAYER + "(" + COLUMN_ID_PLAYER + "));";

        String CREATE_TABLE_STATS = "CREATE TABLE if not exists " + TABLE_NAME_STATS + "(" +
                COLUMN_ID + " int not null primary key , " +
                COLUMN_ACES + " int not null," +
                COLUMN_DOUBLE_FAULTS + " int not null," +
                COLUMN_FIRST_SERVES + " int not null," +
                COLUMN_FORCED_ERRORS + " int not null," +
                COLUMN_POINTS_WON_ON_FIRST_SERVE + " int not null," +
                COLUMN_UNFORCED_ERROR + " int not null," +
                COLUMN_WINNERS + " int not null," +
                COLUMN_SET + " int not null," +
                COLUMN_ID_MATCH + " int not null," +
                COLUMN_ID_PLAYER + " int not null," +
                "FOREIGN KEY (" + COLUMN_ID_MATCH + ") REFERENCES " + TABLE_NAME_GAME + "(" + COLUMN_ID_MATCH + ")," +
                "FOREIGN KEY (" + COLUMN_ID_PLAYER + ") REFERENCES " + TABLE_NAME_PLAYER + "(" + COLUMN_ID_PLAYER + ")  );";

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLAYER);
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_GAME);
        db.execSQL(CREATE_TABLE_STATS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    
    
    public void addPlayer(Player player){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PLAYER,player.getPlayerId());
        values.put(COLUMN_NAME,player.getName());
        System.out.println("Query add player result : " + database.insert(TABLE_NAME_PLAYER,null,values));
    }
    
    public void addGame(Match match){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYER_ONE,match.getFirstPlayer().getPlayerId());
        values.put(COLUMN_PLAYER_TWO,match.getSecondPlayer().getPlayerId());
        values.put(COLUMN_ID_MATCH,match.getMatchId());

        System.out.println("Query add game result : " + database.insert(TABLE_NAME_GAME,null,values));
    }
    
    public void addStats(Statistics statistics){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,statistics.getStatsId());
        values.put(COLUMN_ACES,statistics.getAces());
        values.put(COLUMN_DOUBLE_FAULTS,statistics.getDoubleFaults());
        values.put(COLUMN_FIRST_SERVES,statistics.getFirstServes());
        values.put(COLUMN_FORCED_ERRORS,statistics.getForcedErrors());
        values.put(COLUMN_POINTS_WON_ON_FIRST_SERVE,statistics.getPointsWonOnFirstServe());
        values.put(COLUMN_UNFORCED_ERROR,statistics.getUnforcedErrors());
        values.put(COLUMN_WINNERS,statistics.getWinners());
        values.put(COLUMN_SET,statistics.getSetNumber());
        values.put(COLUMN_ID_MATCH,statistics.getMatchId());
        values.put(COLUMN_ID_PLAYER,statistics.getPlayerId());

        System.out.println("Query add stats : " + database.insert(TABLE_NAME_STATS,null,values));
        
    }
    
    public void emptyDatabase(){
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME_STATS,null,null);
        database.delete(TABLE_NAME_GAME,null,null);
        database.delete(TABLE_NAME_PLAYER,null,null);
    }
}
