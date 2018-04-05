package com.example.sam.hikingoregon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;

import stanford.androidlib.SimpleActivity;

public class RandomHikeActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_hike);
    }

    public void generateRandom(View view) {
        GridLayout grid = $(R.id.grid);
        grid.removeAllViews();

        Ion.with(this)
                .load("https://www.hikingproject.com/data/get-trails?lat=44.9429&lon=-123.0351&maxDistance=50&maxResults=20&key=200232475-fb4ba0aa94a3bc700c5cb0b8eb6a9e6e")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try{
                           Random rand = new Random();
                           int value = rand.nextInt(20);

                           JSONObject json = new JSONObject(result); // retrieves the object from input
                           JSONArray trails = json.getJSONArray("trails"); // gets the array of trails
                           JSONObject one = trails.getJSONObject(value);// gets the object at value of trail array
                           String summary = one.getString("summary"); //returns the data for summary
                           String name = one.getString("name");
                           String length = one.getString("length");
                           String stars = one.getString("stars");
                           String difficulty = one.getString("difficulty");
                           String location = one.getString("location");
                           String highElevation = one.getString("high");
                           String lowElevation = one.getString("low");

                           String image = one.getString("imgMedium");
                           loadImage(image);


                           $TV(R.id.hikeName).setText(name);
                           $TV(R.id.hikeLocation).setText(location);
                           $TV(R.id.hikeSummary).setText(summary); // prints data for summary to a textview
                           $TV(R.id.hikeLength).setText(length);
                           $TV(R.id.hikeStars).setText(stars);
                           $TV(R.id.hikeDifficulty).setText(difficulty);
                           $TV(R.id.hikeHighElevation).setText(highElevation);
                           $TV(R.id.hikeLowElevation).setText(lowElevation);


                        }
                        catch (JSONException jsone) {
                            Log.wtf("help", jsone);
                        }
                    }
                });
    }

    public void loadImage(String url){
        ImageView imgView = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        imgView.setLayoutParams(params);
        GridLayout grid = $(R.id.grid);
        grid.addView(imgView);

        Picasso.with(this)
                .load(url)
                .into(imgView);
    }

    public void clearPage(View view) {
        Intent intent = new Intent(this, RandomHikeActivity.class);
        startActivity(intent);
    }
}
