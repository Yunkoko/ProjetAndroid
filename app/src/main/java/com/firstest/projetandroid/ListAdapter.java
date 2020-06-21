package com.firstest.projetandroid;

import android.content.Context;
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
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImage;
    private Context mcontext;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtHeader;
        TextView txtFooter;
        View layout;
        ImageView image;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader =  v.findViewById(R.id.firstLine);
            txtFooter =  v.findViewById(R.id.secondLine);
            image = v.findViewById(R.id.icon);
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<GMovies> myDataset, ArrayList<String> mImage, Context mcontext) {
        values = myDataset;
        this.mImage = mImage;
        this.mcontext = mcontext;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mcontext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.image);

        final GMovies currentGMovie = values.get(position);
        holder.txtHeader.setText(currentGMovie.getTitle());
        holder.txtFooter.setText(currentGMovie.getUrl());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
