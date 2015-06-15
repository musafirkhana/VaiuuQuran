package com.vaiuu.alquran.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vaiuu.alquran.fragment.QiblaFragment;
import com.vaiuu.alquran.fragment.QuranFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =4;
    private String titles[] ;

    public ViewPagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return QuranFragment.newInstance(position);
            case 1:
                return QiblaFragment.newInstance(position);
            case 2:
                return QuranFragment.newInstance(position);
            case 3:
                return QuranFragment.newInstance(position);



        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}