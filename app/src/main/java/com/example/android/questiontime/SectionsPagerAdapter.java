package com.example.android.questiontime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import static com.example.android.questiontime.MainActivity.QUESTION_COUNT;
import static com.example.android.questiontime.MainActivity.fabResult;

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
        // Return a MainFragment (defined as a static inner class below).

        if(position == QUESTION_COUNT + 3){
            fabResult.setVisibility(View.VISIBLE);
        }
        if(position == 0){
            return WelcomeScreenFragment.newInstance(position +1);
        }
        else if(position == 1){
            return ProfileScreenFragment.newInstance(position +1);
        }
        else if(position == 2){
            return TopicsChoiceFragment.newInstance(position +1);
        }
        else if(position == QUESTION_COUNT + 3){
            return ResultsScreenFragment.newInstance(position +1);
        }
        return MainFragment.newInstance(position +1);
    }

    @Override
    public int getCount() {
        // Show total of pages related to the number of questions and then the welcome and results screens
        return QUESTION_COUNT + 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position < QUESTION_COUNT + 4){
            return "SECTION " + position;
        }
        return null;
    }
}
