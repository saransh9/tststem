package com.tsystem.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.tsystem.R;
import com.tsystem.data.pojo.ApiData;
import com.tsystem.data.pojo.Results;
import com.tsystem.utils.GlideApp;
import com.tsystem.utils.RatioImageView;
import com.tsystem.utils.Utility;

import java.util.ArrayList;

/**
 * Created by saransh on 23/03/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {

    ArrayList<Results> apiData;
    private Context context;
    private AdapterListener mListener;
    int number_columns;


    public MainAdapter(ArrayList<Results> apiData, Context context, final AdapterListener listener, int number_columns) {

        this.apiData = apiData;
        this.context = context;
        this.mListener = listener;
        this.number_columns = number_columns;
    }

    public void setNumber_columns(int number) {
        this.number_columns = number;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        itemView.setOnClickListener(mListener);


        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        int width_new = Utility.getScreenWidth(context, number_columns);
        int width = Integer.parseInt(apiData.get(position).getWidth());
        int height = Integer.parseInt(apiData.get(position).getHeight());
        int height_new = (int) (((double) height / (double) width) * (double) width_new);

        if (position == apiData.size() - 2)
            mListener.loadMoreData();           //loads more data from api

        // holder.images.setImageResource(R.drawable.boax);
        GlideApp.with(context)
                .load(apiData.get(position).getUrls().getRegular())
                .centerCrop()
                .override(width_new, height_new)
                .signature(new ObjectKey(apiData.get(position).getUrls().getRegular()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.images.setBackground(null);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.images);

    }

    @Override
    public int getItemCount() {
        return apiData.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView images;

        viewHolder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.list_image);
        }
    }

    public interface AdapterListener extends View.OnClickListener {
        @Override
        void onClick(View v);

        void loadMoreData();
    }
}
