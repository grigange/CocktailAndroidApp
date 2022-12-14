package com.example.cocktailandroidapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<CocktailModelClass> cocktailList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;


    RelativeLayout internetLayout;
    RelativeLayout noInternetLayout;
    Button tryAgain;


/** Klash h opoia asygxrona sto background xtupaei to api gia dedomena*/
    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL myURL = new URL("https://the-cocktail-db.p.rapidapi.com/popular.php");
                HttpURLConnection URLConnection = (HttpURLConnection) myURL.openConnection();

                URLConnection.setRequestMethod("GET");
                URLConnection.setRequestProperty("X-RapidAPI-Key", "b7cf5674d2msh55f4729e9490592p155a7bjsn5e40837bff16");
                URLConnection.setRequestProperty("X-RapidAPI-Host", "the-cocktail-db.p.rapidapi.com");

                InputStream is = URLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                while (data != -1) {
                    current += (char) data;
                    data = isr.read();
                }
                return current;


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return current;
        }

        /** Epeita me ta dedomena ftiaxnei ena modelo CocktailModelClass gia kathe cocktail kai to vazei se ena List*/
        @Override
        protected void onPostExecute(String s) {
            LinearLayout Loading;
            Loading = findViewById(R.id.loading);
            try {
                JSONObject ob = new JSONObject(s);
                JSONArray a = ob.getJSONArray("drinks");
                for (int i = 0; i < a.length(); i++) {
                    JSONObject item = a.getJSONObject(i);
                    CocktailModelClass model = new CocktailModelClass();
                    model.setId(item.getString("idDrink"));
                    model.setTitle(item.getString("strDrink"));
                    model.setImg(item.getString("strDrinkThumb"));
                    model.setDesc(item.getString("strGlass"));

                    cocktailList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Loading.setVisibility(View.INVISIBLE);
            PutDataIntoRecyclerView(cocktailList);

        }

    }
/**vazei thn Lista cocktailList sto recyclerview mesw tou adaptora*/
    private void PutDataIntoRecyclerView(List<CocktailModelClass> cocktailList) {
        RecyclerAdapter adapter = new RecyclerAdapter(this, cocktailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetLayout = findViewById(R.id.internetLayout);
        noInternetLayout = findViewById(R.id.noInternetLayout);
        tryAgain = findViewById(R.id.try_again_button);
        ImageView image = findViewById(R.id.no_internet_image);

        ImageView gif = findViewById(R.id.gif);
        Glide.with(this).asGif().load(R.drawable.gif).transform(new RoundedCorners(40)).into(gif);

        recyclerView = findViewById(R.id.recycler_view);
        cocktailList = new ArrayList<>();




        /**emfanizetai katallhlo layout se periptwsh pou den yparxei internet*/
        if(isConnected()){
            internetLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.INVISIBLE);
        }
        else{
            noInternetLayout.setVisibility(View.VISIBLE);
            internetLayout.setVisibility(View.INVISIBLE);
        }
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                }
            });




        GetData getData = new GetData();
        getData.execute();


    }

    /**ayth h klash elegxei an o xrhsths exei internet*/
    boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        else{
            return false;
        }

    }
}