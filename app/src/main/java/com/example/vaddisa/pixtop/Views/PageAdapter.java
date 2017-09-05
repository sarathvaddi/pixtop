package com.example.vaddisa.pixtop.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vaddisa.pixtop.Constants;
import com.example.vaddisa.pixtop.ImageOnClick;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    String query;
    boolean mTwoPane;
    ImageOnClick onClick;

    public PageAdapter(FragmentManager fm, String query, boolean mTwoPane, ImageOnClick onClick) {
        super(fm);
        this.query = query;
        this.mTwoPane = mTwoPane;
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    private String tabTitles[] = Constants.tabTitles;

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        GridFragment fragment = GridFragment.newInstance(tabTitles[position], query, mTwoPane, onClick);
        return fragment;
    }

}
