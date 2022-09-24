package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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

    private ArrayList<NoteInfo> CommentsInfoArrayList;
    private DBHandler dbHandler;
    RecyclerView list;
    RecyclerView.LayoutManager list_layout = new LinearLayoutManager(this);
    RecyclerView.Adapter list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView theNote= (TextView)findViewById(R.id.notes) ;
        theNote.setTransitionName(null);


        Intent intent = getIntent();
        String card_id = intent.getStringExtra("ID_REQ");
        String image_url = intent.getStringExtra("IMAGE_URL");
        String title = intent.getStringExtra("TITLE");
        String glass = intent.getStringExtra("GLASS");
        dbHandler = new DBHandler(InfoActivity.this);

        theNote.setText(dbHandler.searchById(card_id));






        TextView view_title = (TextView)findViewById(R.id.title);
        ImageView view_img = (ImageView)findViewById(R.id.img);
        TextView view_desc = (TextView)findViewById(R.id.desc);
        TextView view_recipe = (TextView)findViewById(R.id.recipe);

        theNote.setOnClickListener(new View.OnClickListener() {

            /**on click gia to textview pou paei sto activity me ta notes*/
            @Override
            public void onClick(View v) {

                theNote.setTransitionName("notes");

                Intent i = new Intent(InfoActivity.this, NoteActivity.class);
                i.putExtra("ID_REQ",card_id);
                i.putExtra("IMAGE_URL",image_url);
                i.putExtra("TITLE",title);

                Pair<View, String> titleQ = Pair.create((View)findViewById(R.id.title), "title");
                Pair<View, String> imageQ = Pair.create((View)findViewById(R.id.img), "image");
                Pair<View, String> notesQ = Pair.create((View)findViewById(R.id.notes), "notes");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(InfoActivity.this,titleQ,imageQ,notesQ);
                startActivity(i, options.toBundle());
            }
        });
        list = findViewById(R.id.igr_list);


        view_title.setText(title);
        view_desc.setText(glass);

        Glide.with(this).load(image_url).apply(new RequestOptions().override(450, 450)).transform(new RoundedCorners(50)).into(view_img);
        OkHttpClient client = new OkHttpClient();
/** xtupaei to api*/
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
/** sto success epejergazetai to data gia to kathe ingredient kai to vazei ola mazi sto ListAdapter,vazei to recipe*/
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    InfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject ob = null;
                                ob = new JSONObject(myResponse);
                                String recipe = null;
                                recipe = ob.getJSONArray("drinks").getJSONObject(0).getString("strInstructions");
                                int i = 1;
                                ArrayList<String> Ingredients = new ArrayList<String>();
                                while (i < 15) {
                                    String meas = ob.getJSONArray("drinks").getJSONObject(0).getString("strMeasure" + i);
                                    String igr = ob.getJSONArray("drinks").getJSONObject(0).getString("strIngredient" + i);
                                    if (igr.isEmpty() || igr == "null"){
                                        i = 50;
                                    }else{
                                        Ingredients.add("\u2022  " +   meas + "  "+ igr);
                                        Log.i("IGR",meas + igr);
                                    }
                                    i++;
                                }
                                ListAdapter adapter = new ListAdapter(Ingredients);
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


        /**to koumpi gia na paei sto activity pou epeksergazetai ta notes*/

        Button button1= (Button)findViewById(R.id.addNoteBTN);
        if(dbHandler.searchById(card_id) != ""){
            button1.setText("EDIT NOTES");

        }
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(InfoActivity.this, NoteActivity.class);
                i.putExtra("ID_REQ",card_id);
                i.putExtra("IMAGE_URL",image_url);
                i.putExtra("TITLE",title);

                Pair<View, String> titleQ = Pair.create((View)findViewById(R.id.title), "title");
                Pair<View, String> imageQ = Pair.create((View)findViewById(R.id.img), "image");
                Pair<View, String> notesQ = Pair.create((View)findViewById(R.id.notes), "notes");
                ActivityOptionsCompat options2 = ActivityOptionsCompat.makeSceneTransitionAnimation(InfoActivity.this,titleQ,imageQ,notesQ);
                startActivity(i, options2.toBundle());
            }
        });

        /**to cancel button se paei pisw sto main activity*/
        Button button2= (Button)findViewById(R.id.cancelBTN);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
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
        theNote.setTransitionName(null);
        Button button1= (Button)findViewById(R.id.addNoteBTN);
        if(dbHandler.searchById(card_id) != ""){
            button1.setText("EDIT NOTES");

        }


    }
}
