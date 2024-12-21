package com.example.triviagame;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private TriviaDatabaseManager dbManager;

    @Override

    // Called when the activity is first created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database manager
        dbManager= new TriviaDatabaseManager(this);
        // Set click listener for the start button
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input from the EditText field
                TextInputEditText userNameInput = findViewById(R.id.userName);
                String userNameText = userNameInput.getText().toString().trim();

                // Generate a default username if input is empty
                if(userNameText.isEmpty()){
                    userNameText="User "+ UUID.randomUUID().toString().substring(0,5);
                }

                // Check if the username already exists in the database
                if (!dbManager.isPlayerExists(userNameText)) {
                    // Start the CategoryActivity and pass the username
                    Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                    intent.putExtra("PLAYER",userNameText);
                    startActivity(intent);
                } else {
                    // Show error message if username is already in use
                    Toast.makeText(MainActivity.this, "Player "+userNameText+" is in use", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
