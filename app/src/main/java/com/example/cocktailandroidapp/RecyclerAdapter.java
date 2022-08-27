package com.example.cocktailandroidapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //Variables storing data to display for this example



    private final String[] titles = {"3-Mile Long Island Iced Tea", "Chapter Two", "Chapter Three", "Chapter Four", "Chapter Five",
            "Chapter Six", "Chapter Seven", "Chapter Eight"};
    private final String[] details = {"Item one details", "Item two details", "Item three details", "Item four details", "Item file details", "Item six details", "Item seven details", "Item eight details"};
    private final String[] images = {"https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg","https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg"};


    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;
        Button itemButton;
        public ViewHolder(View itemView) {
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position] + "\n \nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.\n" +
                "\n" +
                "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" +
                "\n" +
                "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate");
        Picasso.get().load(images[position]).resize(300, 300)
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
        return titles.length;

    }


}

