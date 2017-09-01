package com.example.vaddisa.pixtop.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vaddisa.pixtop.BasePresenter;
import com.example.vaddisa.pixtop.ConnectivityManager.ConnectionManager;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;
import com.example.vaddisa.pixtop.Result;

import java.util.ArrayList;

/**
 * Created by vaddisa on 8/21/2017.
 */
public class GridFragment extends android.support.v4.app.Fragment implements Result {

    private static final String TAB_SELECTED_TEXT = "TabSelected";

    public static final String LOCAL_API_URL = "10.0.0.80:8080/photo_gallery/photos";
    public static final String API_STRING = "?key=";
    public static final String QUERY_STRING = "&response_group=high_resolution&q=";
    private static final String POPULAR = "HummingBirds";
    private static final String TOP_RATED = "nature";
    private static final String API_KEY = "6089935-fe0f82301764e844a53159975";
    private static final String BASE_URL = "https://pixabay.com/api/";
    public static final String PIXABAY_HIGH_RES_API = BASE_URL + API_STRING + API_KEY + QUERY_STRING + POPULAR;

    ArrayList<PictureDetails> list;
    RecyclerView recyclerView;
    String tabSelected;
    private BasePresenter basePresenter;

    public static GridFragment newInstance(String tabSelected) {
        Bundle args = new Bundle();
        args.putString(TAB_SELECTED_TEXT, tabSelected);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            basePresenter = new BasePresenter(getContext(), this);
            this.tabSelected = getArguments().getString(TAB_SELECTED_TEXT);
        }
    }

    private void makeNetworkCall(String url, boolean isLocalApi) {
        ConnectionManager cm = new ConnectionManager(basePresenter, getActivity(), isLocalApi);
        cm.setResultInterface(this);
        cm.execute(url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        fetchLatestData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchLatestData();
    }

    private void fetchLatestData() {
        if ("My Album".equalsIgnoreCase(tabSelected)) {
            makeNetworkCall(LOCAL_API_URL, true);
        } else if ("All".equalsIgnoreCase(tabSelected)) {
            makeNetworkCall(PIXABAY_HIGH_RES_API, false);
        } else if ("Saved".equalsIgnoreCase(tabSelected)) {
            basePresenter.fetchFavouritePictures();
        }
    }

    private void setGrid(ArrayList<PictureDetails> results) {
        if (results != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            CustomAdapter customAdapter = new CustomAdapter(getContext(), results);
            recyclerView.setAdapter(customAdapter);

        } else
            Toast.makeText(getContext(), "Failed to get results...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResult(ArrayList<PictureDetails> results) {
        setGrid(results);
    }

    @Override
    public void onError(String serverResponse) {

    }
}
