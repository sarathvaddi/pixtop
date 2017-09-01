package com.example.vaddisa.pixtop.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vaddisa.pixtop.EditDbService;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;

import java.util.ArrayList;

//import com.example.vaddisa.pixtop.EditDbService;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class  DetailsFragment extends android.support.v4.app.Fragment  {

    private static final String STRING = "/10";
    private ImageView moviePoster;
    private RatingBar favBtn;

    private int position;
    private ArrayList<PictureDetails> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.details_fragment, container, false);
        setScreen(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreState(savedInstanceState);
    }

    private void setScreen(View view) {
        moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        favBtn = (RatingBar) view.findViewById(R.id.fav_btn);

    }

    public static DetailsFragment newInstance(int i, ArrayList<PictureDetails> results) {

        Bundle args = new Bundle();

        DetailsFragment fragment = new DetailsFragment();
        args.putParcelableArrayList("results", results);
        args.putInt("position", i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        setValues();
        if (list != null) {

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            list = getArguments().getParcelableArrayList("results");
        }
    }


    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        position = savedInstanceState.getInt("position");
        list = savedInstanceState.getParcelableArrayList("results");

    }

    private void setValues() {
        if (list != null) {
            Glide.with(getActivity()).load(list.get(position).getOverview()).into(moviePoster);
            favBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (favBtn.getRating() == 0) {
                            favBtn.setForeground(getResources().getDrawable(R.drawable.ic_favorite));

                        } else {
                            favBtn.setRating(0);
                            favBtn.setForeground(getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                       saveFavourite();
                    }
                    return true;
                }
            });
        }

    }

    private void saveFavourite() {

        Intent addOrDeleteFav = new Intent(getActivity(), EditDbService.class);
        addOrDeleteFav.putExtra(EditDbService.PICTURES_KEY, list.get(position));
        getActivity().startService(addOrDeleteFav);
    }

    
    public void isFavouriteMovie(boolean isFavouriteMovie) {
        if (isFavouriteMovie)
            favBtn.setRating(1);
        else
            favBtn.setRating(0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (list != null) {
            outState.putParcelableArrayList("results", list);
            outState.putInt("position", position);
            position = getArguments().getInt("position");
            outState.putString("CurrentFragment", "DetailsFragment");
        }
        super.onSaveInstanceState(outState);
    }


}
