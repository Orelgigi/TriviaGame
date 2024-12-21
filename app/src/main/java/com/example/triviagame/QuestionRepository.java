package com.example.triviagame;
import java.util.ArrayList;
import java.util.List;

//This class for add Question
public class QuestionRepository {
    public static List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = new ArrayList<>();
        switch (category) {
            case "Science":
                questions.add(new Question("What is H2O?", new String[]{"Oxygen", "Hydrogen", "Water", "Salt"}, 3));
                questions.add(new Question("Optics is the study of what?", new String[]{"light", "darkness", "Nature", "The Pole Lights"}, 1));
                questions.add(new Question("What is the rarest blood type?", new String[]{"A negative", "AB negative", "O", "AB Positive"}, 2));
                break;
            case "History":
                questions.add(new Question("Who was the first President of the USA?", new String[]{"Lincoln", "Washington", "Adams", "Jefferson"}, 2));
                questions.add(new Question("When did World War I begin? ", new String[]{"1915", "1914", "1922", "1950"}, 2));
                questions.add(new Question("Who is considered the first human technology?", new String[]{"Construction", "Fire", "Iron", "Metal"}, 2));
                break;
            case "Sports":
                questions.add(new Question("How many players are on a soccer team?", new String[]{"9", "10", "11", "12"}, 3));
                questions.add(new Question("Which country won the 2018 World Cup?", new String[]{"France", "Belgium", "Israel", "Brazil"}, 1));
                questions.add(new Question("What sport would you do a touchdown?", new String[]{"Golf", "Hockey", "Wrestling", "Football"}, 4));
                break;
        }//add new question
        return questions;
    }
}