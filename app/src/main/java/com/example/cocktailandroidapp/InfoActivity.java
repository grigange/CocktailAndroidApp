package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoActivity extends AppCompatActivity {

    private ArrayList<CommentsInfo> CommentsInfoArrayList;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView theNote= (TextView)findViewById(R.id.notes) ;

        Intent intent = getIntent();
        String card_id = intent.getStringExtra("ID_REQ");
        String image_url = intent.getStringExtra("IMAGE_URL");
        String title = intent.getStringExtra("TITLE");
        String glass = intent.getStringExtra("GLASS");

        CommentsInfoArrayList = new ArrayList<>();
        dbHandler = new DBHandler(InfoActivity.this);
        /*CommentsInfo note = new CommentsInfo(card_id,"");
        if (!CommentsInfoArrayList.isEmpty()){
            CommentsInfoArrayList = dbHandler.readComments();
            note = CommentsInfoArrayList.get(0);

        }*/
        theNote.setText(dbHandler.searchById(card_id));
        theNote.setGravity(Gravity.CENTER);




        TextView view_title = (TextView)findViewById(R.id.title);
        ImageView view_img = (ImageView)findViewById(R.id.img);
        TextView view_desc = (TextView)findViewById(R.id.desc);
        TextView view_recipe = (TextView)findViewById(R.id.recipe);

        view_title.setText(title);
        view_desc.setText(glass);
        Picasso.get().load(image_url).resize(450, 450)
                .centerCrop().placeholder(R.drawable.ic_launcher_foreground).transform(new RoundedImage(10,10)).into(view_img);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://the-cocktail-db.p.rapidapi.com/lookup.php?i=" + card_id )
                .get()
                .addHeader("X-RapidAPI-Key", "b7cf5674d2msh55f4729e9490592p155a7bjsn5e40837bff16")
                .addHeader("X-RapidAPI-Host", "the-cocktail-db.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    Log.i("MYSDY**************",myResponse);
                    InfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject ob = null;
                            try {
                                ob = new JSONObject(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //getting first and last name
                            String title = null;
                            /*try {
                                title = ob.getJSONArray("drinks").getJSONObject(0).getString("strDrink");
                                view_title.setText(title);
                                view_title.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String img = null;
                            try {
                                img = ob.getJSONArray("drinks").getJSONObject(0).getString("strDrinkThumb");
                                Picasso.get().load(img).resize(300, 300)
                                        .centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(view_img);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            /*String desc = null;
                            try {
                                desc = ob.getJSONArray("drinks").getJSONObject(0).getString("strTags");
                                view_desc.setText(desc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            String recipe = null;
                            try {
                                recipe = ob.getJSONArray("drinks").getJSONObject(0).getString("strInstructions");
                                view_recipe.setText(recipe);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });


        Button button1= (Button)findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(InfoActivity.this, CommentActivity.class);
                i.putExtra("ID_REQ",card_id);
                i.putExtra("IMAGE_URL",image_url);
                i.putExtra("TITLE",title);
                /*ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(InfoActivity.this, (View)view_img, "image");
                v.getContext().startActivity(i, options.toBundle());*/
                startActivity(i);
            }
        });

        Button button2= (Button)findViewById(R.id.button5);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        String card_id = intent.getStringExtra("ID_REQ");
        TextView theNote= (TextView)findViewById(R.id.notes) ;
        theNote.setText(dbHandler.searchById(card_id));


    }
}
