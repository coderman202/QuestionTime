package com.example.android.questiontime;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class to represent all questions with their answers, possible answers, topics,
 * and submitted answers.
 */

class Question {

    private String question, answer, submission;
    private String[] options;
    private Topic topic;

    public Question(String initQuestion, String initAnswer, String[] initOptions, Topic initTopic){
        question = initQuestion;
        answer = initAnswer;
        setOptions(initOptions);
        topic = initTopic;
        submission = "";
    }

    //Second constructor sets topic to General Knowledge if no topic has been specified
    Question(String initQuestion, String initAnswer, String[] initOptions){
        question = initQuestion;
        answer = initAnswer;
        setOptions(initOptions);
        topic = Topic.GENERAL_KNOWLEDGE;
        submission = "";
    }

    Question(String initQuestion){
        question = initQuestion;
    }

    void setAnswer(String initAnswer){
        answer = initAnswer;
    }

    void setTopic(Topic initTopic){
        topic = initTopic;
    }

    void setTopic(int initTopic){
        topic.setValue(initTopic);
    }

    //When setting options, shuffle them up randomly first
    void setOptions(String[] initOptions){
        Random rnd = new Random();
        for (int i = initOptions.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String temp = initOptions[index];
            initOptions[index] = initOptions[i];
            initOptions[i] = temp;
        }
        options = initOptions;
    }

    void setSubmission(String initSubmission){
        submission = initSubmission;
    }

    String getQuestion() {
        return question;
    }

    String getAnswer() {
        return answer;
    }

    String getSubmission() {
        return submission;
    }

    Topic getTopic() {
        return topic;
    }

    String[] getOptions() {
        return options;
    }

    boolean hasTopic(ArrayList<Topic> topicArray){
        for (Topic t:topicArray) {
            if(t.equals(topic)){
                return true;
            }
        }
        return false;
    }

    boolean checkAnswer(){
        return answer.equals(submission);
    }
}
