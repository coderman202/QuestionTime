package com.example.android.questiontime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.android.questiontime.MainActivity.questionCount;

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

        if(position == 0){
            return WelcomeScreenFragment.newInstance(position);
        }
        else if(position == 1){
            return TopicsChoiceFragment.newInstance(position);
        }
        else if(position == 12){
            return ResultsScreenFragment.newInstance(position);
        }
        return MainFragment.newInstance(position + 2);
    }

    @Override
    public int getCount() {
        // Show total of pages related to the number of questions and then the welcome and results screens
        return questionCount + 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position < questionCount){
            return "SECTION " + position;
        }
        return null;
    }
}
