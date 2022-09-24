package com.example.cocktailandroidapp;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class NoteActivity extends AppCompatActivity {

    private EditText theNote;
    private Button addNoteBtn;
    private DBHandler dbHandler;
    private ImageView image_comment;
    private TextView title_comment;
    private ArrayList<NoteInfo> CommentsInfoArrayList;

    /**edw dimiourgitai to activity gia ta notes*/
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
        dbHandler = new DBHandler(NoteActivity.this);

        title_comment.setText(title);


        Glide.with(this).load(image_url).apply(new RequestOptions().override(700, 700)).transform(new RoundedCorners(80)).into(image_comment);

        String comm = dbHandler.searchById(card_id);


        /**elegxos gia to an einai to prwto comment tou xrhsth sto cocktail h thelei na kanei epeksergasia tou paliou*/
        if(comm == ""){
            addNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalComment = theNote.getText().toString();


                    if (finalComment.isEmpty() ) {
                        Toast.makeText(NoteActivity.this, "Please type something", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dbHandler.addNewNote(card_id,finalComment);

                    Toast.makeText(NoteActivity.this, "Note has been added 🍸", Toast.LENGTH_SHORT).show();
                    supportFinishAfterTransition();

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


                    Toast.makeText(NoteActivity.this, "Note Updated 🍸", Toast.LENGTH_SHORT).show();


                    supportFinishAfterTransition();
                }
            });

        }




        /** to cancel button pou se paei sto info activity */
        Button button1= (Button)findViewById(R.id.cancelBTN2);
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });



}}