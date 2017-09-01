package com.example.vaddisa.pixtop.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.vaddisa.pixtop.BasePresenter;
import com.example.vaddisa.pixtop.EditDbService;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//import com.example.vaddisa.pixtop.EditDbService;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class DetailsFragment extends android.support.v4.app.Fragment {

    private static final String STRING = "/10";
    private ImageView moviePoster;
    private RatingBar favBtn;
    private FloatingActionButton share;
    private int position;
    private ArrayList<PictureDetails> list;

    BasePresenter basePresenter;


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
        share = (FloatingActionButton) view.findViewById(R.id.share_fab);
        isFavouriteMovie();

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
            loadImageToImageView();
            setLikeButton();
            setImageSharing();
        }

}

    private void loadImageToImageView() {
        Glide.with(getActivity())
                .load(list.get(position).getOverview())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        moviePoster.setImageDrawable(resource);
                        return true;
                    }
                })
                .into(moviePoster);
    }

    private void setImageSharing() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri bmpUri = getLocalBitmapUri(moviePoster);
                if (bmpUri != null) {
                    // Construct a ShareIntent with link to image
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/*");
                    // Launch sharing dialog for image
                    startActivity(Intent.createChooser(shareIntent, "Share Image"));

                } else {
                    // ...sharing failed, handle error
                }
        }
    });
    }

    private void setLikeButton() {
        favBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (favBtn.getRating() == 0) {
                        favBtn.setRating(1);
                    } else {
                        favBtn.setRating(0);
                    }
                    saveFavourite();
                }
                return true;
            }
        });
    }


    private Uri getLocalBitmapUri(ImageView iview) {
            Drawable drawable = iview.getDrawable();
            Bitmap bmp = null;
            if (drawable instanceof GlideBitmapDrawable){

                bmp = ((GlideBitmapDrawable) iview.getDrawable()).getBitmap();

                Log.e("LOG","Came inside drawable");
            } else {
                Log.e("LOG","drawable is null"+drawable);
                return null;

            }

            Uri bmpUri = null;

            File file =  new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = Uri.fromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmpUri;

        }

    private void saveFavourite() {

        Intent addOrDeleteFav = new Intent(getActivity(), EditDbService.class);
        addOrDeleteFav.putExtra(EditDbService.PICTURES_KEY, list.get(position));
        getActivity().startService(addOrDeleteFav);
    }


    public void isFavouriteMovie() {
        basePresenter = new BasePresenter(getContext());
        if (basePresenter.isFavAvailableInDb(list.get(position).getId_hash()))
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
