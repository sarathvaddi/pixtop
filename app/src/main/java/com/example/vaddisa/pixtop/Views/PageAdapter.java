package com.example.vaddisa.pixtop.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    private String tabTitles[] = new String[]{"All", "My Album", "Saved"};

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        GridFragment fragment = GridFragment.newInstance(tabTitles[position]);
        return fragment;
    }

}
