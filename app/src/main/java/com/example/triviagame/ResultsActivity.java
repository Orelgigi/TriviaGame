package com.example.triviagame;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private TriviaDatabaseManager dbManager;
    private TableLayout allData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);  // Sets the layout for the results screen

        dbManager = new TriviaDatabaseManager(this);  // Initializes the database manager to interact with the database

        // Retrieve score and player name passed from the previous activity
        int score = getIntent().getIntExtra("SCORE", 0);  // Get the score from the Intent extras
        String playerName = getIntent().getStringExtra("NAME");  // Get the player's name from the Intent extras

        dbManager.updatePlayerScore(playerName, score);  // Update the player's score in the database

        // Set the score text in the results screen
        TextView scoreText = findViewById(R.id.scoreText);  // Reference to the TextView displaying the score
        allData = findViewById(R.id.tableLayout);  // Reference to the TableLayout for displaying all players' scores
        String string = "Your Score: " + score;  // Create a string to display the score
        scoreText.setText(string);  // Set the score string in the TextView

        // Retrieve all players' data from the database and display them in a table
        List<Player> players = dbManager.getAllPlayers();  // Get a list of all players from the database
        for (Player player : players) {
            TableRow tableRow = new TableRow(this);  // Create a new TableRow to display each player's information

            // Create a TextView for the player's name and add it to the row
            TextView nameTextView = new TextView(this);
            nameTextView.setText(player.getName());  // Set the player's name
            nameTextView.setPadding(8, 8, 8, 8);  // Set padding around the name
            tableRow.addView(nameTextView);  // Add the name TextView to the TableRow

            // Create a TextView for the player's score and add it to the row
            TextView scoreTextView = new TextView(this);
            scoreTextView.setText(String.valueOf(player.getScore()));  // Set the player's score
            scoreTextView.setPadding(50, 8, 8, 8);  // Set padding around the score
            tableRow.addView(scoreTextView);  // Add the score TextView to the TableRow

            allData.addView(tableRow);  // Add the TableRow to the TableLayout
        }

        // Set click listener for the "Play Again" button to restart the quiz
        findViewById(R.id.playAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CategoryActivity with the player's name passed as an extra
                Intent intent = new Intent(ResultsActivity.this, CategoryActivity.class);
                intent.putExtra("PLAYER", playerName);
                startActivity(intent);
            }
        });

        // Disable the back button behavior so the user cannot navigate back from the results screen
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing when the back button is pressed
            }
        });
    }
}
