package com.example.cocktailandroidapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //Variables storing data to display for this example



    private Context mContext;
    private List<CocktailModelClass> mData;

    public RecyclerAdapter(Context mContext, List<CocktailModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;
        Button itemButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_details);
            itemButton = itemView.findViewById(R.id.item_button);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.card_layout, parent, false);



        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.itemTitle.setText(mData.get(position).getTitle());
        holder.itemDetail.setText(mData.get(position).getDesc());
        Picasso.get().load(mData.get(position).getImg()).resize(300, 300)
                .centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(holder.itemImage);
        holder.itemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(v.getContext(), InfoActivity.class);
                v.getContext().startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();

    }


}

