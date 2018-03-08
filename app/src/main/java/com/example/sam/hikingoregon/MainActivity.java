package com.example.sam.hikingoregon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editHike, editDescription, editLength;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this); //instantiate the dbhelper class

        editHike = (EditText)findViewById(R.id.editText_hike);
        editDescription = (EditText)findViewById(R.id.editText_description);
        editLength = (EditText)findViewById(R.id.editText_length);
        btnAddData = (Button)findViewById(R.id.button_add);
        AddData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editHike.getText().toString(),
                                editDescription.getText().toString(),
                                editLength.getText().toString());
                        if(isInserted = true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
