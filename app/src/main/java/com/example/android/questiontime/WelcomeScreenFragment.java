package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.android.questiontime.MainActivity.STATE_CHOSEN_TOPICS;
import static com.example.android.questiontime.MainActivity.STATE_PLAYER_SCORE;
import static com.example.android.questiontime.MainActivity.STATE_QUESTION_ARRAY;
import static com.example.android.questiontime.MainActivity.chosenTopicList;
import static com.example.android.questiontime.MainActivity.fullQuestionArray;
import static com.example.android.questiontime.MainActivity.playerScore;

/**
 * A fragment containing the welcome screen.
 */
public class WelcomeScreenFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public WelcomeScreenFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WelcomeScreenFragment newInstance(int sectionNumber) {
        WelcomeScreenFragment fragment = new WelcomeScreenFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            playerScore = savedInstanceState.getInt(STATE_PLAYER_SCORE);
            fullQuestionArray = savedInstanceState.getParcelableArrayList(STATE_QUESTION_ARRAY);
            chosenTopicList = savedInstanceState.getParcelableArrayList(STATE_CHOSEN_TOPICS);
        }
        final View rootView = inflater.inflate(R.layout.welcome_screen, container, false);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle saveState){
        saveState.putInt(STATE_PLAYER_SCORE, playerScore);
        saveState.putParcelableArrayList(STATE_QUESTION_ARRAY, fullQuestionArray);
        saveState.putParcelableArrayList(STATE_CHOSEN_TOPICS, chosenTopicList);
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
        }
    }

}
