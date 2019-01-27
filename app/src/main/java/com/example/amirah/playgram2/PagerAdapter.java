package com.example.amirah.playgram2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTab;

    public PagerAdapter(FragmentManager fm, int NumberofTabs){
        super(fm);
        this.numOfTab = NumberofTabs;


    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                TabSchedule tab1 = new TabSchedule();
                return tab1;

            case 1 :
                TabPosted tab2 = new TabPosted();
                return tab2;
                default:
                    return null;
        }


    }

    @Override
    public int getCount() {

        return numOfTab;
    }
}
