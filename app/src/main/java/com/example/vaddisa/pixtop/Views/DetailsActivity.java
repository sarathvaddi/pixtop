package com.example.vaddisa.pixtop.Views;

/**
 * Created by vaddisa on 8/20/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;

import java.util.ArrayList;


/**
 * Created by vaddisa on 1/14/2017.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        int position;
        ArrayList<PictureDetails> list;
        position = getIntent().getIntExtra("position", 0);
        list = getIntent().getParcelableArrayListExtra("results");
        DetailsFragment detailsFragment = DetailsFragment.newInstance(position, list);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.landing_container, detailsFragment, DetailsFragment.class.getSimpleName())
                .commit();

    }
}

