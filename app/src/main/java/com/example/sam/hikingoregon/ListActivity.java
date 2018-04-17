package com.example.sam.hikingoregon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import stanford.androidlib.SimpleActivity;

public class ListActivity extends SimpleActivity {

    int inputLength, textInStars;

    EditText stars;
    EditText minLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        stars = (EditText) findViewById(R.id.ratingBox);
        minLength = (EditText) findViewById((R.id.minLengthBox));
    }

    public void generateList(View view) {
        textInStars = Integer.valueOf(stars.getText().toString());
        inputLength = Integer.valueOf(minLength.getText().toString());
        //showToast(String.valueOf(textInStars));
        //showToast(String.valueOf(inputLength));

    }

    private void showToast(String text){
        Toast.makeText(ListActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
