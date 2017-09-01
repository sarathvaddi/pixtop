package com.example.vaddisa.pixtop.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaddisa.pixtop.R;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class MainFragment extends android.support.v4.app.Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        setScreen(view);
        return view;
    }

    private void setScreen(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        PageAdapter adapter = new PageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.refreshDrawableState();
        tabLayout.setupWithViewPager(viewPager);
    }

}
