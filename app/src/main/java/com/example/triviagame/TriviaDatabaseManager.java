package com.example.triviagame;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class TriviaDatabaseManager {
    private final TriviaDatabaseHelper dbHelper;  // Reference to the TriviaDatabaseHelper to manage database operations

    // Constructor to initialize the database helper
    public TriviaDatabaseManager(Context context) {
        dbHelper = new TriviaDatabaseHelper(context);  // Initialize the database helper with context
    }

    // Updates the player's score or inserts the player if they don't exist
    public void updatePlayerScore(String playerName, int newScore) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // Get a writable database
        ContentValues values = new ContentValues();  // Create a new ContentValues object to store data

        values.put(TriviaDatabaseHelper.COLUMN_NAME, playerName);  // Add player's name to ContentValues
        values.put(TriviaDatabaseHelper.COLUMN_SCORE, newScore);  // Add player's new score to ContentValues

        // Insert or update the player's data, using CONFLICT_REPLACE to update the score if the player already exists
        db.insertWithOnConflict(
                TriviaDatabaseHelper.TABLE_PLAYERS,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE  // Resolve conflicts by replacing existing records
        );
        db.close();  // Close the database after operation
    }

    // Retrieves a list of all players sorted by their score in descending order
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();  // Initialize an empty list to store players
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // Get a readable database

        // Query the player table to get all player data, sorted by score in descending order
        Cursor cursor = db.query(
                TriviaDatabaseHelper.TABLE_PLAYERS,  // Table to query
                null,  // Get all columns
                null, null,  // No WHERE clause (get all rows)
                null, null,  // No GROUP BY or HAVING clause
                TriviaDatabaseHelper.COLUMN_SCORE + " DESC"  // Sort by score in descending order
        );

        // If there are rows in the result set, iterate through them and add players to the list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_NAME));  // Get player's name
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_SCORE));  // Get player's score
                players.add(new Player(name, score));  // Create a Player object and add it to the list
            } while (cursor.moveToNext());  // Move to the next row
        }

        cursor.close();  // Close the cursor
        db.close();  // Close the database
        return players;  // Return the list of players
    }

    // Deletes the database file
    public void deleteDatabase(Context context) {
        dbHelper.deleteDatabase(context);  // Call the helper's method to delete the database
    }

    // Checks if a player already exists in the database
    public boolean isPlayerExists(String playerName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // Get a readable database
        String query = "SELECT 1 FROM " + TriviaDatabaseHelper.TABLE_PLAYERS + " WHERE " + TriviaDatabaseHelper.COLUMN_NAME + " = ?";  // Query to check if player exists
        String[] selectionArgs = { playerName };  // The player's name to check
        Cursor cursor = db.rawQuery(query, selectionArgs);  // Execute the raw query

        boolean exists = cursor.getCount() > 0;  // If count > 0, the player exists
        cursor.close();  // Close the cursor
        db.close();  // Close the database
        return exists;  // Return true if player exists, otherwise false
    }
}