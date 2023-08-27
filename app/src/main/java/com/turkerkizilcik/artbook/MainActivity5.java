package com.turkerkizilcik.artbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity5 extends AppCompatActivity {

    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayList<String> dateArray;
    ArrayAdapter arrayAdapter;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toast.makeText(MainActivity5.this, this.getString(R.string.safe), Toast.LENGTH_LONG).show();

        listView = findViewById(R.id.listView);
        nameArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        idArray = new ArrayList<>();
        database = this.openOrCreateDatabase("FakeArts", MODE_PRIVATE, null);


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

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
                intent.putExtra("artId", idArray.get(i));
                intent.putExtra("info", "old");
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
                intent.putExtra("artId", idArray.get(i));
                intent.putExtra("info", "delete");
                intent.putExtra("position", idArray.get(i));
                startActivity(intent);

                return false;
            }
        });
        getData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
                intent.putExtra("info", "new");
                startActivity(intent);
            }
        });
    }
    public void getData() {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("FakeArts", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM fakearts", null);
            int nameIx = cursor.getColumnIndex("artname");
            int dateIx = cursor.getColumnIndex("date");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                nameArray.add(cursor.getString(nameIx));
                idArray.add(cursor.getInt(idIx));
                dateArray.add(cursor.getString(dateIx));
            }

            arrayAdapter.notifyDataSetChanged();

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

