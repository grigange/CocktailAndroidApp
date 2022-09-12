package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.icu.text.IDNA;
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
    private ArrayList<CommentsInfo> CommentsInfoArrayList;


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
        String image_url = intent.getStringExtra("IMAGE_URL");
        String title = intent.getStringExtra("TITLE");
        dbHandler = new DBHandler(CommentActivity.this);

        title_comment.setText(title);
        Picasso.get().load(image_url).resize(450, 450)
                .centerCrop().transform(new RoundedImage(10,10)).into(image_comment);





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
                    //Intent i = new Intent(CommentActivity.this, InfoActivity.class);
                    //startActivity(i);
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


                    //Intent i = new Intent(CommentActivity.this, InfoActivity.class);
                    //startActivity(i);
                    //supportFinishAfterTransition();
                    finish();
                }
            });

        }





        Button button1= (Button)findViewById(R.id.cancelBTN2);
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //Intent i = new Intent(CommentActivity.this, InfoActivity.class);
                //startActivity(i);
                //supportFinishAfterTransition();
                finish();
            }
        });


    }
}