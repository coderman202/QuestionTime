package com.example.android.questiontime;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

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

    //String Arrays for storing all questions, answers and options
    public static String[][] allQuestions, allAnswers;
    public static String[][][] allOptions;

    //Using an array of custom Question class Questions for combining all
    // questions with their relevant answers and options
    public static Question[] questionArray;

    public EditText username;
    public String user;

    public CoordinatorLayout mainLayout;

    public int playerScore;
    public static int questionCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


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
        int n = qa.length();
        allQuestions = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = qa.getResourceId(i, 0);
            allQuestions[i] = res.getStringArray(id);
        }
        qa.recycle();

        TypedArray aa = res.obtainTypedArray(R.array.all_answers);
        n = aa.length();
        allAnswers = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = aa.getResourceId(i, 0);
            allAnswers[i] = res.getStringArray(id);
        }
        aa.recycle();

        TypedArray oa = res.obtainTypedArray(R.array.all_options);
        n = oa.length();
        allOptions = new String[n][10][];
        for (int i = 0; i < n; ++i) {
            int id = oa.getResourceId(i, 0);
            TypedArray oaInner = res.obtainTypedArray(id);
            int m = oaInner.length();
            for(int j = 0; j < m; j++){
                id = oaInner.getResourceId(j,0);
                allOptions[i][j] = res.getStringArray(id);
            }
            oaInner.recycle();
        }
        oa.recycle();

        Random rnd = new Random();
        for(int j = 0; j < allQuestions.length; j++){
            for(int i = allQuestions[j].length - 1; i > 0; i--){
                int index = rnd.nextInt(i + 1);
                String qs = allQuestions[j][index];
                allQuestions[j][index] = allQuestions[j][i];
                allQuestions[j][i] = qs;

                String as = allAnswers[j][index];
                allAnswers[j][index] = allAnswers[j][i];
                allAnswers[j][i] = as;

                String[] os = allOptions[j][index];
                allOptions[j][index] = allOptions[j][i];
                allOptions[j][i] = os;

                for(int k = 0; k < allOptions[j][i].length; k++){
                    int ind = rnd.nextInt(k + 1);
                    String opt = allOptions[j][i][ind];
                    allOptions[j][i][ind] = allOptions[j][i][k];
                    allOptions[j][i][k] = opt;
                }
            }
        }

        questionArray = new Question[allQuestions[0].length * allQuestions.length];
        int count = 0;
        for(int i = 0; i < allQuestions.length; i++){
            for(int j = 0; j < allQuestions[i].length; j++){
                Question q = new Question(allQuestions[i][j], allAnswers[i][j], allOptions[i][j]);
                q.setTopic(i);
                questionArray[count] = q;
                count++;
            }
        }

        for (int i = questionArray.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Question q = questionArray[i];
            questionArray[i] = questionArray[index];
            questionArray[index] = q;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id==R.id.profile_options){
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.profile_menu);
            dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels/2);
            dialog.setTitle(R.string.profile_title);

            username = (EditText) dialog.findViewById(R.id.username);
            username.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        user = username.getText().toString();
                        Toast.makeText(getApplicationContext(), user, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });

            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Compare the answers and show a snackbar message to display the final score
    public void compareAnswers(View v){
        playerScore = 0;
        for(Question q:questionArray){
            if(q.checkAnswer()){
                playerScore++;
            }
        }
        String message = getString(R.string.result_message, ""+playerScore, ""+questionCount);
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }


}
