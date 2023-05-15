package com.example.rrmuchedzi.vimbisomedicare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rufaro R.Muchedzi on 11/28/2017.
 */

public class DayViewerFragmentsAdapter extends FragmentPagerAdapter {

    private List<Fragment>  fragmentsList = new ArrayList<>();


    public DayViewerFragmentsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragmentsList.add(new MorningDetailsFragment());
        fragmentsList.add(new AfternoonDetailsFragment());
        fragmentsList.add(new EveningDetailsFragment());
        fragmentsList.add(new NightDetailsFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

}
