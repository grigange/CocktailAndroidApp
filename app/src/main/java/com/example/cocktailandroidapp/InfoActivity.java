package com.example.cocktailandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private ArrayList<CommentsInfo> CommentsInfoArrayList;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        CommentsInfoArrayList = new ArrayList<>();
        dbHandler = new DBHandler(InfoActivity.this);

        CommentsInfoArrayList = dbHandler.readComments();
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
