package com.example.android.questiontime;

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
        options = initOptions;
        topic = initTopic;
    }

    //Second constructor sets topic to General Knowledge if no topic has been given
    Question(String initQuestion, String initAnswer, String[] initOptions){
        question = initQuestion;
        answer = initAnswer;
        options = initOptions;
        topic = Topic.GENERAL_KNOWLEDGE;
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

    public void setTopic(Topic initTopic){
        topic = initTopic;
    }

    public void setTopic(int initTopic){
        topic.setValue(initTopic);
    }

    void makeSubmission(String initSubmission){
        submission = initSubmission;
    }

    boolean checkAnswer(){
        return answer.equals(submission);
    }
}
