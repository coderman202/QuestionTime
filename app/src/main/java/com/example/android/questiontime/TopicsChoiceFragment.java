package com.example.android.questiontime;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import static com.example.android.questiontime.MainActivity.STATE_CHOSEN_TOPICS;
import static com.example.android.questiontime.MainActivity.STATE_PLAYER_SCORE;
import static com.example.android.questiontime.MainActivity.STATE_QUESTION_ARRAY;
import static com.example.android.questiontime.MainActivity.chosenTopicList;
import static com.example.android.questiontime.MainActivity.fullQuestionArray;
import static com.example.android.questiontime.MainActivity.playerScore;

/**
 * A fragment containing the topics selection screen.
 */
public class TopicsChoiceFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //An int for looping through the chosen topic list.
    public static int counter = 0;

    public TopicsChoiceFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TopicsChoiceFragment newInstance(int sectionNumber) {
        TopicsChoiceFragment fragment = new TopicsChoiceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.topics_menu, container, false);
        if(savedInstanceState != null){
            playerScore = savedInstanceState.getInt(STATE_PLAYER_SCORE);
            fullQuestionArray = savedInstanceState.getParcelableArrayList(STATE_QUESTION_ARRAY);
            chosenTopicList = savedInstanceState.getParcelableArrayList(STATE_CHOSEN_TOPICS);
        }

        String[] topics = getResources().getStringArray(R.array.question_topics);
        CheckBox[] checkBoxGroup = new CheckBox[topics.length];
        TableLayout layout = (TableLayout) rootView.findViewById(R.id.checkbox_group);
        counter = 0;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            for (int i = 0; i < topics.length; i++) {
                TableRow row = new TableRow(getContext());
                addCheckBoxToRow(checkBoxGroup[i], row, topics, i, 0);
                i++;
                if(i < topics.length){
                    addCheckBoxToRow(checkBoxGroup[i], row, topics, i, 1);
                }
                layout.addView(row);
            }
        }
        else{
            for (int i = 0; i < topics.length; i++) {
                TableRow row = new TableRow(getContext());
                addCheckBoxToRow(checkBoxGroup[i], row, topics, i, 0);
                i++;
                if(i < topics.length){
                    addCheckBoxToRow(checkBoxGroup[i], row, topics, i, 1);
                }
                i++;
                if(i < topics.length){
                    addCheckBoxToRow(checkBoxGroup[i], row, topics, i, 2);
                }
                layout.addView(row);
            }
        }
        return rootView;
    }

    //A method to create and add checkboxes to a row given a position
    // and also to set an oncheckedchange listener
    public void addCheckBoxToRow(CheckBox cb, TableRow row, String[] topics, int index, int layoutPosition){
        cb = new CheckBox(new ContextThemeWrapper(getContext(), R.style.CheckBoxStyle));
        cb.setText(topics[index]);
        cb.setId(index);
        row.addView(cb, layoutPosition);
        final int p = index;
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chosenTopicList.add(Topic.setValue(p));
                    Log.d("Check chosen topic: ", chosenTopicList.get(counter) + "");
                    counter++;
                }
                else{
                    chosenTopicList.remove(Topic.setValue(p));
                    counter--;
                }
            }
        });

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