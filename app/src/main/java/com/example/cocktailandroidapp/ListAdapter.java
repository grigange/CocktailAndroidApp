package com.example.cocktailandroidapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    //Variables storing data to display for this example



    private List<String> mData;

    public ListAdapter( List<String> mData) {
        this.mData = mData;
    }


    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView igr_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            igr_item = itemView.findViewById(R.id.igr_item);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.igr_layout, parent, false);
        return new ViewHolder(v);

    }
/**Gia kathe ingredient kanei to text tou holder analogo*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.igr_item.setText(mData.get(position));


    }


    @Override
    public int getItemCount() {
        return mData.size();

    }


}

