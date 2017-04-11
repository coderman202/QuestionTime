package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.example.android.questiontime.MainActivity.QUESTION_COUNT;
import static com.example.android.questiontime.MainActivity.fullQuestionArray;

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


        //int to store the section number for selecting the question and answers to put in
        //and one to store the index for accessing each question in the ArrayList. Should be
        //secNum - 3 because the first 3 pages are not questions.
        final int secNum = getArguments().getInt(ARG_SECTION_NUMBER);
        final int questionNumber = secNum - 3;

        //Get the submit button and make sure it is hidden until the final question is reached
        Button btn = (Button) rootView.findViewById(R.id.submit_button);
        if(secNum == QUESTION_COUNT + 2){
            btn.setVisibility(View.VISIBLE);
        }
        else{
            btn.setVisibility(View.GONE);
        }

        Log.d("Arraylist Length: ", fullQuestionArray.size()+"");
        MainActivity.getFilteredQuestionArray();
        Log.d("Arraylist Length: ", fullQuestionArray.size()+"");

        q = fullQuestionArray.get(questionNumber);


        //Initialise TextViews for questions and populate them with questions from questionArray
        TextView questionView = (TextView) rootView.findViewById(R.id.question);
        questionView.setText(q.getQuestion());
        final TextView questionNum = (TextView) rootView.findViewById(R.id.question_header);
        questionNum.setText(getString(R.string.question_header, ""+ (secNum - 2)));

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

}
