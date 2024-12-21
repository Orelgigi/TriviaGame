package com.example.triviagame;
import androidx.annotation.NonNull;

public class Player {
    private final String name;
    private int score;

    // Constructor to initialize a player with a name and score
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Getter for the player's name
    public String getName() {
        return name;
    }

    // Setter for the player's score
    public void setScore(int score){
        this.score=score;
    }

    // Getter for the player's score
    public int getScore() {
        return score;
    }

    // Returns a string representation of the player in the format "name:score"
    @NonNull
    @Override
    public String toString() {
        return name+":"+score;
    }
}
