package com.example.android.questiontime;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

    public static String[] questions, submissions, answers, topics;
    public static String[][] options;

    public EditText username;
    public String user;

    public static CheckBox[] topicsChoices;

    public static int playerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //Get arrays of possible questions, answers and options from resource file
        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);

        submissions = new String[answers.length];

        for(int i = 0; i < answers.length; i++){
            submissions[i] = " ";
        }

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.options);
        int n = ta.length();
        options = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            options[i] = res.getStringArray(id);
        }
        ta.recycle();

        Random rnd = new Random();
        for(int i = questions.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            String qs = questions[index];
            questions[index] = questions[i];
            questions[i] = qs;

            String as = answers[index];
            answers[index] = answers[i];
            answers[i] = as;

            String[] os = options[index];
            options[index] = options[i];
            options[i] = os;
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


        if (id == R.id.topics_options) {
            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.topics_menu);
            dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels/2);
            dialog.setTitle(R.string.topics_title);

            LinearLayout checkboxGroup = (LinearLayout) dialog.findViewById(R.id.checkbox_group);

            topics = getResources().getStringArray(R.array.question_topics);
            for(String s:topics){
                CheckBox cbTopic = new CheckBox(getApplicationContext());
                cbTopic.setPadding(40, 10, 20, 10);
                cbTopic.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                cbTopic.setTextSize(16);
                cbTopic.setText(s);
                cbTopic.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(), "You chose: ", Toast.LENGTH_SHORT).show();
                    }
                });
                checkboxGroup.addView(cbTopic);
            }

            Button dialogButton = (Button) dialog.findViewById(R.id.dialog_button_ok);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            return true;
        }
        else if(id==R.id.profile_options){
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

    public void compareAnswers(View v){
        for(int i = 0; i < submissions.length; i++){

            if(submissions[i].equals(answers[i])){
                playerScore++;
            }

        }

        Toast.makeText(this, "You scored: " + playerScore + " out of 5", Toast.LENGTH_SHORT).show();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //int to store the section number for selecting the question and answers to put in
            final int secNum = getArguments().getInt(ARG_SECTION_NUMBER);

            //Get the submit button and make sure it is hidden until the final question is reached
            Button btn = (Button) rootView.findViewById(R.id.submit_button);
            if(secNum == questions.length){
                btn.setVisibility(View.VISIBLE);
            }
            else{
                btn.setVisibility(View.GONE);
            }

            //Initialise TextViews for questions and populate them with string resource text
            TextView questionView = (TextView) rootView.findViewById(R.id.question);
            questionView.setText(questions[secNum - 1]);
            TextView questionNum = (TextView) rootView.findViewById(R.id.question_header);
            questionNum.setText(getString(R.string.question_header, ""+secNum));

            //Initialise the RadioButton group of possible answers for each question
            final RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.options);
            for(int i = 0; i < rg.getChildCount(); i++){
                RadioButton rbn = (RadioButton) rg.getChildAt(i);
                rbn.setText(options[secNum - 1][i]);
            }

            //Set onchecked listener for all radiobuttons
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    submissions[secNum - 1] = "heloo";
                    Log.d("check the id", checkedId+"");

                    checkedId = checkedId % 4;
                    if(checkedId == 0){
                        checkedId = checkedId + 4;
                    }
                    submissions[secNum - 1] = options[secNum - 1][checkedId - 1];

                    Log.d("check the id again", checkedId+"");

                }
            });

            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show total of pages related to the number of questions.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }
    }
}
