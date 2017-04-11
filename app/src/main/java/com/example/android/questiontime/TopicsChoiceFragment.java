package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A fragment containing the topics selection screen.
 */
public class TopicsChoiceFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ArrayList<Topic> chosenTopicList = new ArrayList<>();

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

        String[] topics = getResources().getStringArray(R.array.question_topics);
        CheckBox[] checkBoxGroup = new CheckBox[topics.length];
        counter = 0;
        for (int i = 0; i < topics.length; i++) {
            final int j = i;
            checkBoxGroup[i] = new CheckBox(getContext());
            checkBoxGroup[i].setText(topics[i]);
            checkBoxGroup[i].setId(i);
            checkBoxGroup[i].setTextSize(20);
            checkBoxGroup[i].setPadding(0,10,20,10);

            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.checkbox_group);
            layout.addView(checkBoxGroup[i]);
            checkBoxGroup[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        chosenTopicList.add(Topic.setValue(j));
                        Log.d("Check chosen topic: ", chosenTopicList.get(counter) + "");
                        counter++;
                    }
                    else{
                        chosenTopicList.remove(Topic.setValue(j));
                        counter--;
                    }
                }
            });
        }

        return rootView;
    }

}