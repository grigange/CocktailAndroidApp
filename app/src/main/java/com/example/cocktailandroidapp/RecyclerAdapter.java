package com.example.cocktailandroidapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.card_layout2, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int pos =  holder.getAbsoluteAdapterPosition();
        String glass = mData.get(position).getDesc();
        String emoji = "\uD83C\uDF78  ";
        if (glass.equalsIgnoreCase("Whiskey Glass") || glass.equalsIgnoreCase("Old-fashioned glass")){
            emoji = "\uD83E\uDD43  ";
        }else if (glass.equalsIgnoreCase("Copper Mug")){
            emoji = "\uD83C\uDF7A  ";
        }else if (glass.equalsIgnoreCase("Highball glass")){
            emoji = "\uD83E\uDDCB  ";
        }
        String finalGlass = emoji + glass;
        holder.itemTitle.setText(mData.get(position).getTitle());
        holder.itemDetail.setText(finalGlass);
        Picasso.get().load(mData.get(position).getImg()).resize(300, 300)
                .centerCrop().placeholder(R.drawable.ic_launcher_foreground).transform(new RoundedImage(10,10)).into(holder.itemImage);

        holder.itemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(v.getContext(), InfoActivity.class);
                i.putExtra("ID_REQ",mData.get(position).getId());
                i.putExtra("IMAGE_URL",mData.get(position).getImg());
                i.putExtra("TITLE",mData.get(position).getTitle());
                i.putExtra("GLASS", finalGlass);
                Pair<View, String> title = Pair.create((View)v.findViewById(R.id.item_title), "text");
                Pair<View, String> image = Pair.create((View)v.findViewById(R.id.item_image), "image");
                /*ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity)v.getContext(),title,image);*/
                v.getContext().startActivity(i, /*options.toBundle()*/null);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();

    }


}

