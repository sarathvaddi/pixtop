package com.example.vaddisa.pixtop.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.vaddisa.pixtop.Constants;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;
import com.squareup.picasso.Callback;

import java.util.List;

/**
 * Created by vaddisa on 8/22/2017.
 */
public class GridAdapter extends BaseAdapter {

    private List<PictureDetails> result;
    private Context context;
    ProgressBar spinner;

    public GridAdapter(List result, Context context) {
        this.result = result;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.d("TAG-Adapter", result.size() + Constants.RESULTS);
        return result.size() > 0 ? result.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_view_item, parent, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

        Glide.with(context).load(result.get(position).getOverview()).into(imageView);
        return convertView;
    }


    public class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar) {
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
