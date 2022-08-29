package com.example.cocktailandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private ArrayList<CommentsInfo> CommentsInfoArrayList;
    private DBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView theNote= (TextView)findViewById(R.id.notes) ;

        CommentsInfoArrayList = new ArrayList<>();
        dbHandler = new DBHandler(InfoActivity.this);


        CommentsInfoArrayList = dbHandler.readComments();
        CommentsInfo note = CommentsInfoArrayList.get(0);
        theNote.setText(note.getComment());
        theNote.setGravity(Gravity.CENTER);


        for (CommentsInfo str : CommentsInfoArrayList)
        {
            System.out.println(str.getComment());
        }



        Button button1= (Button)findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(InfoActivity.this, CommentActivity.class);
                startActivity(i);
            }
        });

        Button button2= (Button)findViewById(R.id.button5);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent b = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(b);
            }
        });
    }
}
