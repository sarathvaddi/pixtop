package com.example.vaddisa.pixtop.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.vaddisa.pixtop.BasePresenter;
import com.example.vaddisa.pixtop.Constants;
import com.example.vaddisa.pixtop.PictureDB.EditDbService;
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
    private ImageView poster;
    private RatingBar favBtn;
    private TextView user;
    private TextView userInfo;
    private FloatingActionButton share;
    private int position;
    private ArrayList<PictureDetails> list;
    private int mScrollY;
    private View mPhotoContainerView;
    private int mStatusBarFullOpacityBottom;
    private int mTopInset;
    private int mMutedColor = 0xFF333333;
    private ColorDrawable mStatusBarColorDrawable;
    private static final float PARALLAX_FACTOR = 1.25f;

    private ObservableScrollView mScrollView;
    private DrawInsetsFrameLayout mDrawInsetsFrameLayout;

    BasePresenter basePresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        mDrawInsetsFrameLayout = (DrawInsetsFrameLayout)
                view.findViewById(R.id.draw_insets_frame_layout);
        mDrawInsetsFrameLayout.setOnInsetsCallback(new DrawInsetsFrameLayout.OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                mTopInset = insets.top;
            }
        });

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollview);
        mScrollView.setCallbacks(new ObservableScrollView.Callbacks() {
            @Override
            public void onScrollChanged() {
                mScrollY = mScrollView.getScrollY();
                mPhotoContainerView.setTranslationY((int) (mScrollY - mScrollY / PARALLAX_FACTOR));
                updateStatusBar();
            }
        });

        poster = (ImageView) view.findViewById(R.id.poster);
        mPhotoContainerView = view.findViewById(R.id.photo_container);

        mStatusBarColorDrawable = new ColorDrawable(0);
        setScreen(view);
        updateStatusBar();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreState(savedInstanceState);
    }

    private void setScreen(View view) {
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setDuration(200);
        favBtn = (RatingBar) view.findViewById(R.id.fav_btn);
        share = (FloatingActionButton) view.findViewById(R.id.share_fab);
        user = (TextView) view.findViewById(R.id.user);
        userInfo = (TextView) view.findViewById(R.id.user_info);
        isFavouriteMovie();

    }

    public static DetailsFragment newInstance(int i, ArrayList<PictureDetails> results) {

        Bundle args = new Bundle();

        DetailsFragment fragment = new DetailsFragment();
        args.putParcelableArrayList(Constants.RESULTS, results);
        args.putInt(Constants.POSITION, i);
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
            position = getArguments().getInt(Constants.POSITION);
            list = getArguments().getParcelableArrayList(Constants.RESULTS);
        }
    }


    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        position = savedInstanceState.getInt(Constants.POSITION);
        list = savedInstanceState.getParcelableArrayList(Constants.RESULTS);

    }

    private void setValues() {
        if (list != null) {
            loadImageToImageView();
            setLikeButton();
            setImageSharing();
            user.setText(Constants.PHOTOGRAPHER + list.get(position).getOriginal_title());
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
                        poster.setImageDrawable(resource);
                        return true;
                    }
                })
                .into(poster);
    }

    private void setImageSharing() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri bmpUri = getLocalBitmapUri(poster);
                if (bmpUri != null) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, Constants.SHARE_IMAGE));
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
        if (drawable instanceof GlideBitmapDrawable) {

            bmp = ((GlideBitmapDrawable) iview.getDrawable()).getBitmap();
        } else {
            return null;

        }

        Uri bmpUri = null;

        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
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
        if (basePresenter.isFavAvailableInDb(list != null ? list.get(position).getId_hash() : null))
            favBtn.setRating(1);
        else
            favBtn.setRating(0);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (list != null) {
            outState.putParcelableArrayList(Constants.RESULTS, list);
            outState.putInt(Constants.POSITION, position);
            position = getArguments().getInt(Constants.POSITION);
            outState.putString(Constants.CURRENTFRAGMENT, Constants.DETAILSFRAG);
        }
        super.onSaveInstanceState(outState);
    }

    private void updateStatusBar() {
        int color = 0;
        if (poster != null && mTopInset != 0 && mScrollY > 0) {
            float f = progress(mScrollY,
                    mStatusBarFullOpacityBottom - mTopInset * 3,
                    mStatusBarFullOpacityBottom - mTopInset);
            color = Color.argb((int) (255 * f),
                    (int) (Color.red(mMutedColor) * 0.9),
                    (int) (Color.green(mMutedColor) * 0.9),
                    (int) (Color.blue(mMutedColor) * 0.9));
        }
        mStatusBarColorDrawable.setColor(color);
        mDrawInsetsFrameLayout.setInsetBackground(mStatusBarColorDrawable);
    }

    static float progress(float v, float min, float max) {
        return constrain((v - min) / (max - min), 0, 1);
    }

    static float constrain(float val, float min, float max) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

}
