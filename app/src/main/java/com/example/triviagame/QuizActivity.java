package com.example.triviagame;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class QuizActivity extends AppCompatActivity {
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private TextView timerText;
    private Player player;
    private  CountDownTimer countdowntimer;
    // Declare an array of button IDs
    private final int[] optionButtonIds = {
            R.id.option1, R.id.option2, R.id.option3, R.id.option4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Retrieve the category and player's name from the intent
        String category = getIntent().getStringExtra("CATEGORY");
        String playerName = getIntent().getStringExtra("PLAYER");
        player = new Player(playerName, 0);  // Initialize the player with name and starting score of 0
        timerText = findViewById(R.id.timerText);  // Reference to the TextView showing the timer

        // Fetch questions based on the selected category
        questions = QuestionRepository.getQuestionsByCategory(category);
        displayQuestion();  // Display the first question

        // Disable the back button functionality
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });

        // Initialize and start the countdown timer (30 seconds for each question)
        countdowntimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time: " + millisUntilFinished / 1000);  // Update timer text every second
            }

            public void onFinish() {
                currentQuestionIndex++;  // Move to the next question when the time is up
                displayQuestion();
            }
        };
        countdowntimer.start();  // Start the countdown timer
    }

    // Displays the current question and its options
    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            showResults();  // If all questions are answered, show the results
            return;
        }

        // Get the current question and shuffle its options
        Question currentQuestion = questions.get(currentQuestionIndex);
        List<String> shuffledOptions = new ArrayList<>(Arrays.asList(currentQuestion.getOptions()));
        Collections.shuffle(shuffledOptions);

        // Determine the new shuffled index of the correct answer
        int shuffledCorrectIndex = shuffledOptions.indexOf(currentQuestion.getCorrectAnswer());
        currentQuestion.setShuffledCorrectOptionIndex(shuffledCorrectIndex);  // Store the shuffled correct index

        // Update the UI with the current question and shuffled options
        TextView questionText = findViewById(R.id.questionText);
        questionText.setText(currentQuestion.getQuestion());

        Button button1=findViewById(optionButtonIds[0]);
        Button button2=findViewById(optionButtonIds[1]);
        Button button3=findViewById(optionButtonIds[2]);
        Button button4=findViewById(optionButtonIds[3]);
        // Set the shuffled options to the buttons
        button1.setText(shuffledOptions.get(0));
        button2.setText(shuffledOptions.get(1));
        button3.setText(shuffledOptions.get(2));
        button4.setText(shuffledOptions.get(3));

        // Set click listeners for each option button to check the answer
        button1.setOnClickListener(v -> checkAnswer(currentQuestion, 0));
        button2.setOnClickListener(v -> checkAnswer(currentQuestion, 1));
        button3.setOnClickListener(v -> checkAnswer(currentQuestion, 2));
        button4.setOnClickListener(v -> checkAnswer(currentQuestion, 3));
    }

    // Checks whether the selected answer is correct
    private void checkAnswer(Question currentQuestion, int selectedOptionIndex) {
        int shuffledCorrectIndex = currentQuestion.getShuffledCorrectOptionIndex();
        Button selectedButton = findViewById(optionButtonIds[selectedOptionIndex]);
        // Check if the selected answer is correct and update the player's score
        if (shuffledCorrectIndex == selectedOptionIndex) {
            player.setScore(player.getScore() + 5);  // Award 5 points for a correct answer
            selectedButton.setBackgroundColor(Color.GREEN);  // Change button color to green for correct answer
        } else {
            selectedButton.setBackgroundColor(Color.RED);  // Change button color to red for incorrect answer
        }

        // Reset the button color after a delay, cancel the current timer, and start a new one for the next question
        selectedButton.postDelayed(() -> {
            selectedButton.setBackgroundColor(Color.TRANSPARENT);  // Reset button color
            countdowntimer.cancel();  // Cancel the current timer
            countdowntimer.start();  // Start a new countdown timer for the next question
            currentQuestionIndex++;  // Move to the next question
            displayQuestion();  // Display the next question
        }, 500);  // Delay for 500 milliseconds to show the result
    }

    // Show the results screen when the quiz is over
    private void showResults() {
        // Create an intent to start the ResultsActivity
        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        // Pass the player's score to the ResultsActivity
        intent.putExtra("SCORE", player.getScore());
        // Pass the player's name to the ResultsActivity
        intent.putExtra("NAME", player.getName());
        // Cancel the countdown timer to stop it before transitioning to the results screen
        countdowntimer.cancel();
        // Finish the current activity (QuizActivity) to remove it from the back stack
        finish();
        // Start the ResultsActivity to display the results
        startActivity(intent);
    }
}