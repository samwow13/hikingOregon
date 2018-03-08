package com.example.sam.hikingoregon;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
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
    Button btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this); //instantiate the dbhelper class

        editHike = (EditText)findViewById(R.id.editText_hike);
        editDescription = (EditText)findViewById(R.id.editText_description);
        editLength = (EditText)findViewById(R.id.editText_length);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_view);
        AddData();
        viewAll();
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

    public void viewAll() {
        btnViewAll.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    Cursor res = myDB.getAllData();
                    if (res.getCount() == 0) {
                        //show message
                        showMessage("Error", "Nothing found");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()) {
                        buffer.append("Id : " + res.getString(0) + "\n");
                        buffer.append("Hike : " + res.getString(1) + "\n");
                        buffer.append("Description : " + res.getString(2) + "\n");
                        buffer.append("Length : " + res.getString(3) + "\n\n");
                    }
                    showMessage("Data", buffer.toString());
                }
            }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
