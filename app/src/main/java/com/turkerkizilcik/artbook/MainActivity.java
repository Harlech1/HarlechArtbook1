package com.turkerkizilcik.artbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    //ListView listView1;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayList<String> dateArray;
    ArrayAdapter arrayAdapter, arrayAdapter1;
    SQLiteDatabase database;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listView);
        //listView1 = findViewById(R.id.listView1);
        nameArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        idArray = new ArrayList<>();
        database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameArray);
        //arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,dateArray);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, nameArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);


                // Generate ListView Item using TextView
                return view;
            }
        };
            /*
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, dateArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(12);

                // Generate ListView Item using TextView
                return view;
            }
        };

             */


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("info", "new");
                startActivity(intent);
            }
        });

        listView.setAdapter(arrayAdapter);
        //listView1.setAdapter(arrayAdapter1);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("artId", idArray.get(i));
                intent.putExtra("info", "old");
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("artId", idArray.get(i));
                intent.putExtra("info", "delete");
                intent.putExtra("position", idArray.get(i));
                startActivity(intent);

                return false;
            }
        });
        getData();
    }


    public void getData() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM arts", null);
            int nameIx = cursor.getColumnIndex("artname");
            int dateIx = cursor.getColumnIndex("date");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                nameArray.add(cursor.getString(nameIx));
                idArray.add(cursor.getInt(idIx));
                dateArray.add(cursor.getString(dateIx));
            }

            arrayAdapter.notifyDataSetChanged();
            //arrayAdapter1.notifyDataSetChanged();

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}