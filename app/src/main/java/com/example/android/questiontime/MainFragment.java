package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.example.android.questiontime.MainActivity.STATE_CHOSEN_TOPICS;
import static com.example.android.questiontime.MainActivity.STATE_PLAYER_SCORE;
import static com.example.android.questiontime.MainActivity.STATE_QUESTION_ARRAY;
import static com.example.android.questiontime.MainActivity.chosenTopicList;
import static com.example.android.questiontime.MainActivity.fullQuestionArray;
import static com.example.android.questiontime.MainActivity.playerScore;

/**
 * A fragment containing the questions.
 */
public class MainFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Question variable to store to current question for each page.
    // Created to make code more concise.
    public static Question q = new Question("");

    public static final String CURRENT_QUESTION = "CurrentQuestion";

    public MainFragment() {
    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if(savedInstanceState != null){
            playerScore = savedInstanceState.getInt(STATE_PLAYER_SCORE);
            fullQuestionArray = savedInstanceState.getParcelableArrayList(STATE_QUESTION_ARRAY);
            chosenTopicList = savedInstanceState.getParcelableArrayList(STATE_CHOSEN_TOPICS);
            q = savedInstanceState.getParcelable(CURRENT_QUESTION);

            Log.d("Check states: ", fullQuestionArray.get(0).getQuestion());
        }



        //int to store the section number for selecting the question and answers to put in
        //and one to store the index for accessing each question in the ArrayList. Should be
        //secNum - 3 because the first 3 pages are not questions.
        final int secNum = getArguments().getInt(ARG_SECTION_NUMBER);
        final int questionNumber = secNum - 4;

        Log.d("Arraylist Length: ", fullQuestionArray.size()+"");
        MainActivity.getFilteredQuestionArray();
        Log.d("Arraylist Length: ", fullQuestionArray.size()+"");

        q = fullQuestionArray.get(questionNumber);

        //Initialise TextViews for questions and populate them with questions from questionArray
        TextView questionView = (TextView) rootView.findViewById(R.id.question);
        questionView.setText(q.getQuestion());
        final TextView questionNum = (TextView) rootView.findViewById(R.id.question_header);
        questionNum.setText(getString(R.string.question_header, ""+ (secNum - 3)));

        //Initialise the RadioButton group of possible answers for each question
        final RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.options);
        for(int i = 0; i < rg.getChildCount(); i++){
            RadioButton rbn = (RadioButton) rg.getChildAt(i);
            rbn.setText(q.getOptions()[i]);
        }

        //Set onchecked listener for all radiobuttons
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = checkedId % 4;
                if(checkedId == 0){
                    checkedId = checkedId + 4;
                }
                fullQuestionArray.get(questionNumber).setSubmission(fullQuestionArray.get(questionNumber).getOptions()[checkedId - 1]);
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle saveState){
        saveState.putInt(STATE_PLAYER_SCORE, playerScore);
        saveState.putParcelableArrayList(STATE_QUESTION_ARRAY, fullQuestionArray);
        saveState.putParcelableArrayList(STATE_CHOSEN_TOPICS, chosenTopicList);
        saveState.putParcelable(CURRENT_QUESTION, q);
        Log.d("Check statev: ", fullQuestionArray.get(0).getQuestion());
        super.onSaveInstanceState(saveState);

    }
    //Restore instance here
    @Override
    public void onViewStateRestored(Bundle restoreState) {
        super.onViewStateRestored(restoreState);
        if(restoreState!=null){
            playerScore = restoreState.getInt(STATE_PLAYER_SCORE);
            fullQuestionArray = restoreState.getParcelableArrayList(STATE_QUESTION_ARRAY);
            chosenTopicList = restoreState.getParcelableArrayList(STATE_CHOSEN_TOPICS);
            q = restoreState.getParcelable(CURRENT_QUESTION);
            Log.d("Check stater: ", fullQuestionArray.get(0).getQuestion());
        }
    }

}
