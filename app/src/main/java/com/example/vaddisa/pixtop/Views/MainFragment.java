package com.example.vaddisa.pixtop.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaddisa.pixtop.ImageOnClick;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;

import java.util.ArrayList;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class MainFragment extends android.support.v4.app.Fragment implements ImageOnClick{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    String query;
    private boolean mTwoPane;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null)
            query = bundle.getString("query");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        if (view.findViewById(R.id.landing_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.landing_container, new DetailsFragment(), "DetailsFragment")
                        .commit();
            }
        }else
            mTwoPane =false;
        setHasOptionsMenu(true);
        setScreen(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setScreen(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), query,mTwoPane,this);
        viewPager.setAdapter(adapter);
        viewPager.refreshDrawableState();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onImageClick(ArrayList<PictureDetails> results, int position) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.landing_container, DetailsFragment.newInstance(position,results), "DetailsFragment")
                .commit();
    }
}
