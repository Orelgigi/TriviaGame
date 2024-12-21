package com.example.triviagame;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TriviaDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trivia.db";  // Name of the database
    private static final int DATABASE_VERSION = 1;  // Version of the database
    public static final String TABLE_PLAYERS = "player";  // Name of the table for storing player data
    public static final String COLUMN_ID = "_id";  // Column name for the player ID
    public static final String COLUMN_NAME = "name";  // Column name for the player's name
    public static final String COLUMN_SCORE = "score";  // Column name for the player's score

    // SQL statement to create the 'player' table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PLAYERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Unique ID for each player
                    COLUMN_NAME + " TEXT UNIQUE , " +  // Player's name (unique)
                    COLUMN_SCORE + " INTEGER" +  // Player's score (integer)
                    ");";

    // Deletes the trivia database
    public void deleteDatabase(Context context) {
        context.deleteDatabase(TriviaDatabaseHelper.DATABASE_NAME);  // Delete the database file
    }

    // Constructor for the database helper
    public TriviaDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // Initialize the database helper with context, database name, and version
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);  // Executes the SQL statement to create the player table
    }

    // Called when the database is upgraded (e.g., changing version)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);  // Drop the old table if it exists
        onCreate(db);  // Create the table again
    }
}

