package com.firstest.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<GMovies> values;
    private OnMovieListener mOnMovieListener;
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImage;
    private Context mContext;

    public ListAdapter(List<GMovies> myDataset, ArrayList<String> mImage, Context mContext, OnMovieListener onMovieListener) {
        values = myDataset;
        this.mImage = mImage;
        this.mContext = mContext;
        this.mOnMovieListener = onMovieListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtHeader;
        TextView txtFooter;
        OnMovieListener onMovieListener;
        View layout;
        ImageView image;

        public ViewHolder(@NonNull View v, OnMovieListener onMovieListener) {
            super(v);
            layout = v;
            txtHeader =  v.findViewById(R.id.firstLine);
            txtFooter =  v.findViewById(R.id.secondLine);
            image = v.findViewById(R.id.icon);
            this.onMovieListener = onMovieListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    public void add(int position, GMovies item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, mOnMovieListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.image);

        final GMovies currentGMovie = values.get(position);
        holder.txtHeader.setText(currentGMovie.getTitle());
        holder.txtFooter.setText(currentGMovie.getUrl());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    public interface OnMovieListener
    {
        void onMovieClick(int position);
    }
}
