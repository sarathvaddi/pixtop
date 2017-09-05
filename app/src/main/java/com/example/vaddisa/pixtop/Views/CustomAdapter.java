package com.example.vaddisa.pixtop.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vaddisa.pixtop.Constants;
import com.example.vaddisa.pixtop.ImageOnClick;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;

import java.util.ArrayList;

/**
 * Created by vaddisa on 8/25/2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    ArrayList<PictureDetails> results;
    Context context;
    private int mSelectedItem = 0;
    boolean mTwoPane;
    ImageOnClick onClick;

    public CustomAdapter(Context context, ArrayList<PictureDetails> results, boolean mTwoPane, ImageOnClick onClick) {
        this.context = context;
        this.results = results;
        this.mTwoPane = mTwoPane;
        this.onClick = onClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bindData(final int position) {
            Glide.with(context).load(results.get(position).getOverview()).into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mTwoPane) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(Constants.POSITION, position);
                        intent.putParcelableArrayListExtra(Constants.RESULTS, results);
                        context.startActivity(intent);
                    } else {
                        onClick.onImageClick(results, position);
                    }
                }
            });

        }
    }
}

