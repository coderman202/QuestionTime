package com.example.android.questiontime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class to represent all questions with their answers, possible answers, topics,
 * and submitted answers.
 */

public class Question implements Parcelable{

    private String question, answer;
    private String submission = "Unanswered";
    private String[] options;
    private Topic topic;

    protected Question(Parcel in){
        question = in.readString();
        answer = in.readString();
        options = in.createStringArray();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }
        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(question);
        out.writeString(answer);
        out.writeStringArray(options);
    }
    public Question(String initQuestion, String initAnswer, String[] initOptions, Topic initTopic){
        question = initQuestion;
        answer = initAnswer;
        setOptions(initOptions);
        topic = initTopic;
    }

    //Second constructor sets topic to General Knowledge if no topic has been specified
    Question(String initQuestion, String initAnswer, String[] initOptions){
        question = initQuestion;
        answer = initAnswer;
        setOptions(initOptions);
        topic = Topic.GENERAL_KNOWLEDGE;
    }

    public Question(String initQuestion){
        question = initQuestion;
    }

    public void setAnswer(String initAnswer){
        answer = initAnswer;
    }

    public void setTopic(Topic initTopic){
        topic = initTopic;
    }

    public void setTopic(int initTopic){
        topic.setValue(initTopic);
    }

    //When setting options, shuffle them up randomly first
    public void setOptions(String[] initOptions){
        Random rnd = new Random();
        for (int i = initOptions.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String temp = initOptions[index];
            initOptions[index] = initOptions[i];
            initOptions[i] = temp;
        }
        options = initOptions;
    }

    public void setSubmission(String initSubmission){
        submission = initSubmission;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSubmission() {
        return submission;
    }

    public String getTopicString(){
        return topic.getStringValue();
    }
    public Topic getTopic() {
        return topic;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean hasTopic(ArrayList<Topic> topicArray){
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
