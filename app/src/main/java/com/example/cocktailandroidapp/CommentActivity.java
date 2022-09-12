package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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


public class CommentActivity extends AppCompatActivity {

    private EditText theNote;
    private Button addNoteBtn;
    private DBHandler dbHandler;
    private ImageView image_comment;
    private TextView title_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        theNote= findViewById(R.id.notes2);
        addNoteBtn = findViewById(R.id.saveNotesBTN);
        title_comment = findViewById(R.id.title_comment);
        image_comment = findViewById(R.id.image_comment);

        Intent intent = getIntent();
        String card_id = intent.getStringExtra("ID_REQ");
        dbHandler = new DBHandler(CommentActivity.this);



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
                    CommentActivity.this.runOnUiThread(new Runnable() {
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
                                title_comment.setText(title);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String img = null;
                            try {
                                img = ob.getJSONArray("drinks").getJSONObject(0).getString("strDrinkThumb");
                                Picasso.get().load(img).resize(300, 300)
                                        .centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(image_comment);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });


        String comm = dbHandler.searchById(card_id);
        Log.i("DP HANDLER",comm);

        if(comm == ""){
            addNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalComment = theNote.getText().toString();


                    if (finalComment.isEmpty() ) {
                        Toast.makeText(CommentActivity.this, "Please type something", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dbHandler.addNewNote(card_id,finalComment);

                    Toast.makeText(CommentActivity.this, "Note has been added 🍸", Toast.LENGTH_SHORT).show();

                    finish();

                }
            });
        }
        else{

            theNote.setText(dbHandler.searchById(card_id));
            addNoteBtn.setText("Update Notes");
            addNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalComment = theNote.getText().toString();
                    dbHandler.updateNote(dbHandler.searchById(card_id),finalComment, card_id);


                    Toast.makeText(CommentActivity.this, "Note Updated 🍸", Toast.LENGTH_SHORT).show();


                    finish();
                }
            });

        }






        Button button1= (Button)findViewById(R.id.cancelBTN2);
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }
}