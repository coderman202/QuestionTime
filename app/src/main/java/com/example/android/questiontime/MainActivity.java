package com.example.android.questiontime;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static com.example.android.questiontime.R.id.fab_restart;
import static com.example.android.questiontime.R.id.fab_result;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    public static Topic[] allTopics = {
            Topic.SPORTS, Topic.GEOGRAPHY,
            Topic.HISTORY, Topic.SCIENCE, Topic.MUSIC};

    //Using an arraylist of custom Question class Questions for combining all
    // questions with their relevant answers and options
    public static ArrayList<Question> fullQuestionArray = new ArrayList<>();

    //Also arraylist of topics for storing all chosen topics
    public static ArrayList<Topic> chosenTopicList = new ArrayList<>();

    public CoordinatorLayout mainLayout;

    public static int playerScore;

    public static String user;
    public static String emailAddress;
    public static final int QUESTION_COUNT = 10;

    //States for recalling scores and questions;
    public static final String STATE_PLAYER_SCORE = "PlayerScore";
    public static final String STATE_QUESTION_ARRAY = "QuestionArray";
    public static final String STATE_CHOSEN_TOPICS = "ChosenTopics";
    public static final String STATE_USER  = "StateUsername";
    public static final String STATE_EMAIL  = "StateEmail";

    public static FloatingActionButton fabResult, fabRestart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            playerScore = savedInstanceState.getInt(STATE_PLAYER_SCORE);
            fullQuestionArray = savedInstanceState.getParcelableArrayList(STATE_QUESTION_ARRAY);
            chosenTopicList = savedInstanceState.getParcelableArrayList(STATE_CHOSEN_TOPICS);
            user = savedInstanceState.getString(STATE_USER);
            emailAddress = savedInstanceState.getString(STATE_EMAIL);

        }

        setContentView(R.layout.activity_main);

        fabResult = (FloatingActionButton) findViewById(fab_result);
        fabRestart = (FloatingActionButton) findViewById(fab_restart);

        mainLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        Resources res = getResources();
        TypedArray qa = res.obtainTypedArray(R.array.all_questions);
        TypedArray aa = res.obtainTypedArray(R.array.all_answers);
        TypedArray oa = res.obtainTypedArray(R.array.all_options);
        int n = qa.length();
        Log.d("length of n", n+"");

        //String Arrays for getting all questions, answers and options
        // before moving them into question ArrayList
        String [][] allQuestions = new String[n][];
        String [][] allAnswers = new String[n][];
        String [][][] allOptions = new String[n][10][];
        for (int i = 0; i < n; ++i) {
            int id = qa.getResourceId(i, 0);
            allQuestions[i] = res.getStringArray(id);
            id = aa.getResourceId(i, 0);
            allAnswers[i] = res.getStringArray(id);
            id = oa.getResourceId(i, 0);
            TypedArray oaInner = res.obtainTypedArray(id);
            int m = oaInner.length();
            for(int j = 0; j < m; j++){
                id = oaInner.getResourceId(j,0);
                allOptions[i][j] = res.getStringArray(id);
            }
            oaInner.recycle();
            Log.d("Check length: ", allQuestions[0].length + "");
            for (int j = 0; j < allQuestions[0].length; j++) {
                Question q = new Question(allQuestions[i][j], allAnswers[i][j], allOptions[i][j]);
                fullQuestionArray.add(q);
                Log.d("Question is: ", q.getQuestion());
            }
        }
        qa.recycle();
        aa.recycle();
        oa.recycle();
        Log.d("Arraylist pre shuffle: ", allQuestions.length * allQuestions[0].length+"");
        Log.d("Arraylist pre shuffle: ", fullQuestionArray.size()+"");

        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < allQuestions[0].length; j++) {
                Log.d("Count val: ", count+"");
                fullQuestionArray.get(count).setTopic(allTopics[i]);
                count++;
            }
        }

        //A shuffle method for randomly shuffling the questions
        long seed = System.nanoTime();

        Collections.shuffle(fullQuestionArray, new Random(seed));

        Log.d("Arraylist post shuffl: ", fullQuestionArray.size()+"");
    }

    @Override
    public void onSaveInstanceState(Bundle saveState){
        saveState.putInt(STATE_PLAYER_SCORE, playerScore);
        saveState.putParcelableArrayList(STATE_QUESTION_ARRAY, fullQuestionArray);
        saveState.putParcelableArrayList(STATE_CHOSEN_TOPICS, chosenTopicList);
        saveState.putString(STATE_USER, user);
        saveState.putString(STATE_EMAIL, emailAddress);
        super.onSaveInstanceState(saveState);
    }
    //Restore instance here
    @Override
    public void onRestoreInstanceState(Bundle restoreState) {
        super.onRestoreInstanceState(restoreState);
        playerScore = restoreState.getInt(STATE_PLAYER_SCORE);
        fullQuestionArray = restoreState.getParcelableArrayList(STATE_QUESTION_ARRAY);
        chosenTopicList = restoreState.getParcelableArrayList(STATE_CHOSEN_TOPICS);
        user = restoreState.getString(STATE_USER);
        emailAddress = restoreState.getString(STATE_EMAIL);
    }

    public void restartGame(View view){
        finish();
        startActivity(getIntent());
    }

    public void getQuestions(View v){
        getFilteredQuestionArray();
    }


    //Loops through original question array to check for questions selected by chosen
    // topics and add them to the filtered question list. If no topics are chosen, add all topics.
    public static void getFilteredQuestionArray(){
        if(chosenTopicList.isEmpty()){
            chosenTopicList.addAll(Arrays.asList(allTopics));
        }
        for (Topic t:chosenTopicList) {
            Log.d("Topic: ", ""+t);

        }

        Iterator<Question> i = fullQuestionArray.iterator();
        while (i.hasNext()){
            Question q = i.next();
            Log.d("Check value: ", ""+q.hasTopic(chosenTopicList));
            if(!q.hasTopic(chosenTopicList)){
                i.remove();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    * This closes the keyboard once you click anywhere on the screen outside the EditText
    * This solution is thanks to: http://stackoverflow.com/users/840558/daniel
    * Found here: https://tinyurl.com/lctudx6
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event "+event.getRawX()+","+event.getRawY()+" "+x+","+y+" rect "+w.getLeft()+","+w.getTop()+","+w.getRight()+","+w.getBottom()+" coords "+scrcoords[0]+","+scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    //Compare the answers and show a snackbar message to display the final score
    public void compareAnswers(View v){
        playerScore = 0;
        for (int i = 0; i < QUESTION_COUNT; i++) {
            if(fullQuestionArray.get(i).checkAnswer()) {
                playerScore++;
            }

        }
        String message = getString(R.string.result_message, ""+user, ""+playerScore, ""+QUESTION_COUNT);
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();

    }
    public static void compareAnswers(){
        playerScore = 0;
        for (int i = 0; i < QUESTION_COUNT; i++) {
            if(fullQuestionArray.get(i).checkAnswer()) {
                playerScore++;
            }
        }
    }


}
