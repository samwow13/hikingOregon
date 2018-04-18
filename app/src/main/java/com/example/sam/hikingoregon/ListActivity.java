package com.example.sam.hikingoregon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import stanford.androidlib.SimpleActivity;

public class ListActivity extends SimpleActivity {

    int inputLength, textInStars;



    EditText stars;
    EditText minLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }

    public void generateList(View view) {

        //pulls data from textboxes
        stars = (EditText) findViewById(R.id.ratingBox);
        minLength = (EditText) findViewById((R.id.minLengthBox));

        //converts data in boxes to strings
        textInStars = Integer.valueOf(stars.getText().toString());
        inputLength = Integer.valueOf(minLength.getText().toString());



        //showToast(String.valueOf(textInStars));
        //showToast(String.valueOf(inputLength));
        Ion.with(this)
                //this url takes in method parameters, you can check the api for more details.
                .load("https://www.hikingproject.com/data/get-trails?lat=44.9429&lon=-123.0351&maxDistance=40&minStars=" + textInStars + "&minLength=" + inputLength + "&maxResults=30&key=200232475-fb4ba0aa94a3bc700c5cb0b8eb6a9e6e")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {


                            JSONObject json = new JSONObject(result); // retrieves the object from input
                            JSONArray trails = json.getJSONArray("trails"); // gets the array of trails

                            JSONObject one = trails.getJSONObject(0);// gets the object at value of trail array

                            //returns data for each of the following

                            String name = one.getString("name");
                            String length = one.getString("length");

                            String highElevation = one.getString("high");
                            String lowElevation = one.getString("low");
                            int high = Integer.parseInt(highElevation);
                            int low = Integer.parseInt(lowElevation);
                            int elevationGain = high - low;
                            String gain = Integer.toString(elevationGain);

                            String image = one.optString("imgSmall");// gets the image
                            if(image.isEmpty()){
                                image = "https://dummyimage.com/600x400/000/fff&text=No+Image+Exists";
                            }
                            loadImage(image);

                            //sets textviews to proper inputs
                            $TV(R.id.hikeListName).setText(name);
                            $TV(R.id.hikeListLength).setText(length);
                            $TV(R.id.hikeHighElevation).setText(gain);



                            LinearLayout layout = (LinearLayout) findViewById(R.id.activityList);// where we add the dynamic layout
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );


                            for(int i = 1; i < trails.length() - 1;i++){
                                JSONObject x = trails.getJSONObject(i);
                                String hikeName = x.getString("name");
                                String hikeLength = x.getString("length");
                                String imagez = x.optString("imgSmall");

                                String h = x.getString("high");
                                    String l = x.getString("low");
                                    int highz = Integer.parseInt(h);
                                    int lowz = Integer.parseInt(l);
                                    int elevationGainz = highz - lowz;
                                    String gainz = Integer.toString(elevationGainz);

                                try {
                                    addList(imagez, hikeName, hikeLength, gainz, layout);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                        } catch (JSONException jsone) {
                            Log.wtf("help", jsone);
                        }
                    }
                });

    }

    public void addList(String image, String hikeName, String hikeLength, String gains, LinearLayout layout) throws IOException {
        View list = getLayoutInflater().inflate(R.layout.list, null);

        ImageView img = (ImageView) list.findViewById(R.id.listImage);
        URL url = new URL(image);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        Picasso.with(context)
//                .load(image)
//                .resize(200, 200)
//                .into(img);


        img.setImageBitmap(bmp);


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
    public void loadImage(String url) {
            ImageView imgView = new ImageView(this);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.height = 300;
            params.width = 300;

            imgView.setLayoutParams(params);
            GridLayout grid = $(R.id.gridList);
            grid.removeAllViews();
            grid.addView(imgView);

            Picasso.with(this)
                    .load(url)
                    .into(imgView);


    }




    private void showToast(String text){
        Toast.makeText(ListActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
