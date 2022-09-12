package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView theNote= (TextView)findViewById(R.id.notes) ;

        Intent intent = getIntent();
        String card_id = intent.getStringExtra("ID_REQ");


        dbHandler = new DBHandler(InfoActivity.this);
        theNote.setText(dbHandler.searchById(card_id));


        TextView view_title = (TextView)findViewById(R.id.title);
        ImageView view_img = (ImageView)findViewById(R.id.img);
        TextView view_desc = (TextView)findViewById(R.id.desc);
        TextView view_recipe = (TextView)findViewById(R.id.recipe);

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
                            try {
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
                            }
                            String desc = null;
                            try {
                                desc = ob.getJSONArray("drinks").getJSONObject(0).getString("strIngredient1");
                                view_desc.setText(desc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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


        Button button1= (Button)findViewById(R.id.addNoteBTN);
        if(dbHandler.searchById(card_id) != ""){
            button1.setText("UPDATE NOTES");

        }



        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(InfoActivity.this, CommentActivity.class);
                i.putExtra("ID_REQ",card_id);
                startActivity(i);
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
        Button button1 = (Button)findViewById(R.id.addNoteBTN);
        if(dbHandler.searchById(card_id) != ""){
            button1.setText("UPDATE NOTES");

        }




    }
}
