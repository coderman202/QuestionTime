package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment containing the welcome screen.
 */
public class TopicsChoiceFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

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

        return rootView;
    }

}