package com.example.sam.hikingoregon;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.XMLReader;


import stanford.androidlib.SimpleActivity;

import static android.widget.Toast.makeText;


public class MainActivity extends SimpleActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void goToList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void goToSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

//    public void goToRandomHike(View view) {
//        Intent intent = new Intent(this, RandomHikeActivity.class);
//        startActivity(intent);
//        Ion.with(this)
//                .load("https://www.hikingproject.com/data/get-trails?lat=44.9429&lon=123.0351&maxDistance=50&maxResults=1&key=200232475-fb4ba0aa94a3bc700c5cb0b8eb6a9e6e")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        try{
//                            JSONObject json = XML.toJSONObject(result);
//
//                            JSONObject a = json.getJSONObject("trails")
//                                    .getJSONObject("summary");
//
//                            for(int i = 0;i < a.length();i++) {
//                                JSONObject img = a.getJSONObject(i);
//                                String url = img.getString("summary");
//                            }
//                        }
//                        catch (JSONException jsone) {
//                            Log.wtf("help", jsone);
//                        }
//                    }
//                });
//
//    }


    public void goToRandomHike(View view) {
        Intent intent = new Intent(this, RandomHikeActivity.class);
        startActivity(intent);

    }
}
