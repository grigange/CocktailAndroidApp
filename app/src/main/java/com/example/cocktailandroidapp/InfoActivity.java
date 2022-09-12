package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.ContextThemeWrapper;
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
    RecyclerView list;
    RecyclerView.LayoutManager list_layout = new LinearLayoutManager(this);
    RecyclerView.Adapter list_adapter;

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
        list = findViewById(R.id.igr_list);


        view_title.setText(title);
        view_desc.setText(glass);
        Picasso.get().load(image_url).resize(450, 450)
                .centerCrop().placeholder(R.drawable.ic_cocktail_shaker_svgrepo_com).transform(new RoundedImage(50,10)).into(view_img);

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

                            try {
                                JSONObject ob = null;
                                ob = new JSONObject(myResponse);
                                String recipe = null;
                                recipe = ob.getJSONArray("drinks").getJSONObject(0).getString("strInstructions");
                                int i = 1;
                                ArrayList<String> Igredients = new ArrayList<String>();
                                while (i < 15) {
                                    String meas = ob.getJSONArray("drinks").getJSONObject(0).getString("strMeasure" + i);
                                    String igr = ob.getJSONArray("drinks").getJSONObject(0).getString("strIngredient" + i);
                                    if (igr.isEmpty() || igr == "null"){
                                        i = 50;
                                    }else{
                                        Igredients.add("\u2022  " +   meas + "  "+ igr);
                                        Log.i("IGR",meas + igr);
                                    }
                                    i++;
                                }
                                ListAdapter adapter = new ListAdapter(Igredients);
                                list.setLayoutManager(list_layout);
                                list.setAdapter(adapter);
                                view_recipe.setText(recipe);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });


        Button button1= (Button)findViewById(R.id.addNoteBTN);
        if(dbHandler.searchById(card_id) != ""){
            button1.setText("UPDATE NOTES");

        }
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(InfoActivity.this, CommentActivity.class);
                i.putExtra("ID_REQ",card_id);
                i.putExtra("IMAGE_URL",image_url);
                i.putExtra("TITLE",title);

                /*Pair<View, String> titleQ = Pair.create((View)v.findViewById(R.id.item_image), ViewCompat.getTransitionName((View)v.findViewById(R.id.item_image)));
                Pair<View, String> imageQ = Pair.create((View)v.findViewById(R.id.item_image), "image");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(InfoActivity.this,titleQ);*/
                startActivity(i/*, options.toBundle()*/);
            }
        });

        Button button2= (Button)findViewById(R.id.cancelBTN);
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
        Button button1= (Button)findViewById(R.id.addNoteBTN);
        if(dbHandler.searchById(card_id) != ""){
            button1.setText("UPDATE NOTES");

        }


    }
}
