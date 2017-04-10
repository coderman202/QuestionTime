package com.example.android.questiontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        final View rootView = inflater.inflate(R.layout.welcome_screen, container, false);

        return rootView;
    }

}
