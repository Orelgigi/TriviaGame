package com.example.triviagame;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;


public class CategoryActivity extends AppCompatActivity {
    String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Display a welcome message with the player's name
        TextView textView = findViewById(R.id.HelloUser);
        playerName = getIntent().getStringExtra("PLAYER");

        // Set click listeners for each category button
        findViewById(R.id.categoryScience).setOnClickListener(v -> startQuiz("Science"));
        findViewById(R.id.categoryHistory).setOnClickListener(v -> startQuiz("History"));
        findViewById(R.id.categorySports).setOnClickListener(v -> startQuiz("Sports"));

        // Update the welcome message with the player's name
        String title_name = "Hello " + playerName;
        textView.setText(title_name);

        // Disable the back button behavior
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });
    }

    // Starts the quiz activity with the selected category and player's name
    private void startQuiz(String category) {
        Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
        intent.putExtra("CATEGORY", category);
        intent.putExtra("PLAYER", playerName);
        startActivity(intent);
    }
}
