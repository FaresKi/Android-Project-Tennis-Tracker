package fr.android.tennistracker.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import fr.android.tennistracker.Model.Player;

public class MyDBHandler extends SQLiteOpenHelper {
    
    public static final String TABLE_NAME_STATS = "Statistics";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACES = "aces";
    public static final String COLUMN_DOUBLE_FAULTS = "double_faults";
    public static final String COLUMN_FIRST_SERVES = "first_serves";
    public static final String COLUMN_FORCED_ERRORS = "forced_errors";
    public static final String COLUMN_POINTS_WON_ON_FIRST_SERVE = "points_won_on_first_serve";
    public static final String COLUMN_UNFORCED_ERROR = "unforced_error";
    public static final String COLUMN_WINNERS = "winners";
    public static final String COLUMN_SET = "set_number";

    public static final String TABLE_NAME_HISTORY = "History";
    public static final String COLUMN_PLAYER_ONE = "player_one";
    public static final String COLUMN_PLAYER_TWO = "player_two";
    public static final String COLUMN_ID_MATCH = "id_match";

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tennis-tracker.db";

    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_HISTORY = "create table " + TABLE_NAME_HISTORY +
                "(\n" +
                COLUMN_ID_MATCH+ "int not null primary key autoincrement,"+
                COLUMN_PLAYER_ONE + "text not null,"+
                COLUMN_PLAYER_TWO + "text not null );";

        String CREATE_TABLE_STATS = "CREATE TABLE " + TABLE_NAME_STATS + "(" +
                COLUMN_ID + " int not null primary key autoincrement, " +
                COLUMN_ACES + " int not null," +
                COLUMN_DOUBLE_FAULTS + " int not null," +
                COLUMN_FIRST_SERVES + "int not null," +
                COLUMN_FORCED_ERRORS + "int not null," +
                COLUMN_POINTS_WON_ON_FIRST_SERVE + "int not null," +
                COLUMN_UNFORCED_ERROR + "int not null," +
                COLUMN_WINNERS + "int not null," +
                COLUMN_SET + "int not null,"+
                COLUMN_ID_MATCH + "int not null,"+
                "FOREIGN KEY ("+COLUMN_ID_MATCH+") REFERENCES "+TABLE_NAME_HISTORY+"("+COLUMN_ID_MATCH + "));";
        

        db.execSQL(CREATE_TABLE_STATS);
        db.execSQL(CREATE_TABLE_HISTORY);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addHandler(Player player) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_NAME_HISTORY, null, contentValues);
        database.close();
    }
}
