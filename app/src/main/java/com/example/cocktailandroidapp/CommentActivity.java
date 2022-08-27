package com.example.cocktailandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity {

    private EditText theComment;
    private Button addCommentBtn;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        theComment= findViewById(R.id.comments);
        addCommentBtn = findViewById(R.id.button7);
        dbHandler = new DBHandler(CommentActivity.this);
        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalComment = theComment.getText().toString();

                if (finalComment.isEmpty() ) {
                    Toast.makeText(CommentActivity.this, "Please type something", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHandler.addNewComment(finalComment);

                Toast.makeText(CommentActivity.this, "Comment has been added.", Toast.LENGTH_SHORT).show();
                theComment.setText("");
            }
        });

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