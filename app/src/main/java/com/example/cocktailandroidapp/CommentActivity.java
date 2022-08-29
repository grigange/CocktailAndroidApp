package com.example.cocktailandroidapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class CommentActivity extends AppCompatActivity {

    private EditText theNote;
    private Button addNoteBtn;
    private DBHandler dbHandler;
    private ArrayList<CommentsInfo> CommentsInfoArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        theNote= findViewById(R.id.comments);
        addNoteBtn = findViewById(R.id.button7);
        dbHandler = new DBHandler(CommentActivity.this);
        CommentsInfoArrayList = new ArrayList<>();
        CommentsInfoArrayList = dbHandler.readComments();

        if(CommentsInfoArrayList.isEmpty()){
            addNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalComment = theNote.getText().toString();


                    if (finalComment.isEmpty() ) {
                        Toast.makeText(CommentActivity.this, "Please type something", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dbHandler.addNewComment(finalComment);

                    Toast.makeText(CommentActivity.this, "Note has been added.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CommentActivity.this, InfoActivity.class);
                    startActivity(i);

                }
            });
        }
        else{
            CommentsInfo note = CommentsInfoArrayList.get(0);
            theNote.setText(note.getComment());
            addNoteBtn.setText("Update Notes");
            addNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalComment = theNote.getText().toString();
                    dbHandler.updateNote(note.getComment(),finalComment);


                    Toast.makeText(CommentActivity.this, "Note Updated..", Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(CommentActivity.this, InfoActivity.class);
                    startActivity(i);
                }
            });

        }





        Button button1= (Button)findViewById(R.id.button6);
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(CommentActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });


    }
}