package com.example.triviagame;

public class Question {
    private String questionText;
    private String[] options;
    private int correctOptionIndex;
    private int shuffledCorrectOptionIndex;

    // Constructor to initialize the question with text, options, and the correct answer index
    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    // Getter for the question text
    public String getQuestion() {
        return questionText;
    }

    // Getter for the options array
    public String[] getOptions() {
        return options;
    }

    // Getter for the index of the correct option
    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    // Returns the correct answer based on the original index
    public String getCorrectAnswer() {
        return options[correctOptionIndex - 1];
    }

    // Setter for the shuffled correct option index
    public void setShuffledCorrectOptionIndex(int index) {
        this.shuffledCorrectOptionIndex = index;
    }

    // Getter for the shuffled correct option index
    public int getShuffledCorrectOptionIndex() {
        return shuffledCorrectOptionIndex;
    }
}