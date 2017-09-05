package com.example.vaddisa.pixtop.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vaddisa.pixtop.BasePresenter;
import com.example.vaddisa.pixtop.ConnectivityManager.ConnectionManager;
import com.example.vaddisa.pixtop.Constants;
import com.example.vaddisa.pixtop.ImageOnClick;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;
import com.example.vaddisa.pixtop.Result;

import java.util.ArrayList;

import static com.example.vaddisa.pixtop.Constants.LOCAL_API_URL;
import static com.example.vaddisa.pixtop.Constants.PIXABAY_HIGH_RES_API;
import static com.example.vaddisa.pixtop.Constants.QUERY;
import static com.example.vaddisa.pixtop.Constants.TAB_SELECTED_TEXT;

/**
 * Created by vaddisa on 8/21/2017.
 */
public class GridFragment extends android.support.v4.app.Fragment implements Result {


    ArrayList<PictureDetails> list;
    RecyclerView recyclerView;
    String tabSelected;
    String query;
    TextView errorText;
    boolean mTwoPane;
    static ImageOnClick onClickI;
    private BasePresenter basePresenter;


    public static GridFragment newInstance(String tabSelected, String query, boolean mTwoPane, ImageOnClick onClick) {
        Bundle args = new Bundle();
        args.putString(TAB_SELECTED_TEXT, tabSelected);
        args.putString(Constants.query, query);
        args.putBoolean(Constants.TWO_PANE, mTwoPane);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        onClickI = onClick;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            basePresenter = new BasePresenter(getContext(), this);
            this.tabSelected = getArguments().getString(TAB_SELECTED_TEXT);
            this.query = getArguments().getString(Constants.query);
            this.mTwoPane = getArguments().getBoolean(Constants.TWO_PANE);
            ;
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
        errorText = (TextView) view.findViewById(R.id.error_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchLatestData();
    }

    private void fetchLatestData() {
        if (Constants.MY_ALBUM.equalsIgnoreCase(tabSelected)) {
            makeNetworkCall(LOCAL_API_URL, true);
        } else if (Constants.ALL.equalsIgnoreCase(tabSelected)) {
            if (query != null)
                makeNetworkCall(PIXABAY_HIGH_RES_API + query, false);
            else
                makeNetworkCall(PIXABAY_HIGH_RES_API + QUERY, false);
        } else if (Constants.SAVED.equalsIgnoreCase(tabSelected)) {
            basePresenter.fetchFavouritePictures(getLoaderManager());
        }
    }

    public void setGrid(final ArrayList<PictureDetails> results) {
        if (results != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mTwoPane ? 1 : 3));
            final CustomAdapter customAdapter = new CustomAdapter(getContext(), results, mTwoPane, onClickI);
            recyclerView.setAdapter(customAdapter);
        } else {
            errorText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void getResult(ArrayList<PictureDetails> results) {
        setGrid(results);
        list = results;
    }

    @Override
    public void onError(String serverResponse) {

    }
}
