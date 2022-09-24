package com.example.cocktailandroidapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_details);
            itemCard = itemView.findViewById(R.id.card_view);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.card_layout2, parent, false);

        return new ViewHolder(v);

    }


    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_out_row);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int findRelativeAdapterPositionIn(@NonNull RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, @NonNull RecyclerView.ViewHolder viewHolder, int localPosition) {
        return super.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition);
    }
/**gia kathe cocktail tropopoiei to data,to vazei sto holder kai to kanei animate*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_in_row);

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
        Glide.with(mContext).load(mData.get(position).getImg()).apply(new RequestOptions().override(175, 175)).transform(new RoundedCorners(20)).into(holder.itemImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
/**Ean kaneis click to item tou recyclerview pairnaei sto intent ta shared data kai ta shared elements gia to transition*/
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mContext, InfoActivity.class);
                i.putExtra("ID_REQ",mData.get(position).getId());
                i.putExtra("IMAGE_URL",mData.get(position).getImg());
                i.putExtra("TITLE",mData.get(position).getTitle());
                i.putExtra("GLASS", finalGlass);

                Pair<View, String> card = Pair.create((View)holder.itemCard, "card");
                Pair<View, String> title = Pair.create((View)holder.itemTitle, "title");
                Pair<View, String> image = Pair.create((View)holder.itemImage, "image");
                Pair<View, String> desc = Pair.create((View)holder.itemDetail, "desc");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,card,desc,title,image);

                mContext.startActivity(i, options.toBundle());
            }
        });
        holder.itemView.startAnimation(animation);


    }


    @Override
    public int getItemCount() {
        return mData.size();

    }


}

