package com.example.sam.hikingoregon;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import stanford.androidlib.SimpleActivity;

public class searchTwo extends SimpleActivity {

    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_two);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void generateSearch(View view) {

        input = (EditText) findViewById((R.id.input));
        final String test  = input.getText().toString().toLowerCase();
        //showToast(test);
        Ion.with(this)
                .load("https://www.hikingproject.com/data/get-trails?lat=45.5231&lon=-122.6765&sort=quality&maxDistance=60&maxResults=100&key=200232475-fb4ba0aa94a3bc700c5cb0b8eb6a9e6e")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            LinearLayout layout = (LinearLayout) findViewById(R.id.activitySearch);// where we add the dynamic layout


                            JSONObject json = new JSONObject(result); // retrieves the object from input
                            JSONArray trails = json.getJSONArray("trails"); // gets the array of trails



                            for(int i = 0; i < 90 -1 ;i++){
                                JSONObject x = trails.getJSONObject(i);
                                String hikeNameCheck = x.getString("name").toLowerCase();
                                String subHike = hikeNameCheck.substring(0, test.length());
                                //showToast("subhike = " + subHike);

                                //indexOf
                                if(hikeNameCheck.contains(test)) {


                                    String hikeName = x.getString("name");
                                    String hikeLength = x.getString("length");
                                    String imagez = x.optString("imgSmall");

                                    //calculates the elevation gain.
                                    String h = x.getString("high");
                                    String l = x.getString("low");
                                    int highz = Integer.parseInt(h);
                                    int lowz = Integer.parseInt(l);
                                    int elevationGainz = highz - lowz;
                                    String gainz = Integer.toString(elevationGainz);

                                    try {
                                        addListItem(imagez, hikeName, hikeLength, gainz, layout);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException jsone) {
                            Log.wtf("help", jsone);
                        }
                    }
                });
    }



    public void addListItem(String image, String hikeName, String hikeLength, String gains, LinearLayout layout) throws IOException {

        if (image.isEmpty()) {
            image = "https://dummyimage.com/600x400/000/fff&text=No+Image+Exists";
        }

        View list = getLayoutInflater().inflate(R.layout.list, null);

        ImageView img = (ImageView) list.findViewById(R.id.listImage);
        loadImage(image, img);


        TextView tvHikeName = (TextView) list.findViewById(R.id.listHikeName);
        tvHikeName.setText(hikeName);

        TextView tvHikeLength = (TextView) list.findViewById(R.id.listHikeLength);
        tvHikeLength.setText(hikeLength);

        TextView tvHikeGainz = (TextView) list.findViewById(R.id.listElevationGain);
        tvHikeGainz.setText(gains);

        layout.addView(list);

    }




    /*
    Method that takes in a url and programmatically exports the image given to a certain location within the random hike.
     */
    public void loadImage(String url, ImageView imgView) {
        Picasso.with(this)
                .load(url)
                .into(imgView);
    }




    private void showToast(String text){
        Toast.makeText(searchTwo.this, text, Toast.LENGTH_SHORT).show();
    }
}
