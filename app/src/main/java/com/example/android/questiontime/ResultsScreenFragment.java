package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.example.android.questiontime.MainActivity.QUESTION_COUNT;
import static com.example.android.questiontime.MainActivity.fullQuestionArray;
import static com.example.android.questiontime.MainActivity.playerScore;

/**
 * A fragment containing the welcome screen.
 */
public class ResultsScreenFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ResultsScreenFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ResultsScreenFragment newInstance(int sectionNumber) {
        ResultsScreenFragment fragment = new ResultsScreenFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.results_screen, container, false);

        TableLayout resultsTable = (TableLayout) rootView.findViewById(R.id.results_table);
        /**
         * Add rows to the results table, 1 for each question answered.
         * Get the correct styles for the rows and change the colour of the choice textview to
         * represent whether you were right or wrong
         */
        for (int i = 0; i < QUESTION_COUNT; i++) {
            TableRow row = new TableRow(getContext());

            TextView answer = new TextView(new ContextThemeWrapper(getContext(), R.style.TableRows));
            TextView choice = new TextView(new ContextThemeWrapper(getContext(), R.style.TableRows));
            answer.setText(fullQuestionArray.get(i).getAnswer());
            choice.setText(fullQuestionArray.get(i).getSubmission());
            if(fullQuestionArray.get(i).checkAnswer()){
                choice.setTextColor(ContextCompat.getColor(getContext(), R.color.correctAnswer));
            }
            else{
                choice.setTextColor(ContextCompat.getColor(getContext(), R.color.wrongAnswer));
            }
            row.addView(answer, 0);
            row.addView(choice, 1);
            resultsTable.addView(row);
        }
        TextView score = (TextView) rootView.findViewById(R.id.result_score);
        score.setText(getString(R.string.result_message, playerScore+"", QUESTION_COUNT+""));

        return rootView;
    }

}
