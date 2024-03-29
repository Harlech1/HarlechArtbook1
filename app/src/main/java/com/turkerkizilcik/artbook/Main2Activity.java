package com.turkerkizilcik.artbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    EditText artNameText, painterNameText;
    Button button;
    Button button2;
    Button button3;
    LinearLayout linearLayout3;
    SQLiteDatabase database;
    TextView textView;
    DateFormat df = new SimpleDateFormat("h:mm dd.MM.yyyy");// sout'u 5:09 24.06.2021
    String date = df.format(Calendar.getInstance().getTime());// sout'u 5:09 24.06.2021


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        artNameText = findViewById(R.id.artNameText);
        painterNameText = findViewById(R.id.painterNameText);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
        linearLayout3 = findViewById(R.id.linearLayout3);


        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if (info.matches("delete")) {
            button.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = linearLayout3.getLayoutParams();
            params.width = 1200;
            try {
                int artId = intent.getIntExtra("artId", 1);
                Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE id == ?", new String[]{String.valueOf(artId)});
                int artNameIx = cursor.getColumnIndex("artname");
                int painterNameIx = cursor.getColumnIndex("paintername");
                int dateIx = cursor.getColumnIndex("date");

                while (cursor.moveToNext()) {
                    artNameText.setText(cursor.getString(artNameIx));
                    painterNameText.setText(cursor.getString(painterNameIx));
                    //currentTime = cursor.getString(dateIx);
                }

                cursor.close();

            } catch (Exception e) {

            }
        } else if (info.matches("new")) {
            artNameText.setText("");
            painterNameText.setText("");
            button.setVisibility(View.VISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = linearLayout3.getLayoutParams();
            params.width = 1200;

        }else {
            //düzenleme yeri
                int artId = intent.getIntExtra("artId", 1);
                button.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = linearLayout3.getLayoutParams();
                params.width = 1200;
                try {


                    Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE id == ?", new String[]{String.valueOf(artId)});
                    int artNameIx = cursor.getColumnIndex("artname");
                    int painterNameIx = cursor.getColumnIndex("paintername");
                    int dateIx = cursor.getColumnIndex("date");


                    while (cursor.moveToNext()) {
                        artNameText.setText(cursor.getString(artNameIx));
                        painterNameText.setText(cursor.getString(painterNameIx));
                    }
                    cursor.close();
                } catch (Exception e) {
                }
        }
    }


    public void save(View view) {

        String artName = artNameText.getText().toString();
        String painterName = painterNameText.getText().toString();


        try {
            database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, date VARCHAR)");
            String sqlString = "INSERT INTO arts(artname, paintername, date) VALUES (?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, artName);
            sqLiteStatement.bindString(2, painterName);
            sqLiteStatement.bindString(3, date);
            sqLiteStatement.execute();
            Toast.makeText(Main2Activity.this, this.getString(R.string.notunuzkaydedildi),Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(Main2Activity.this, this.getString(R.string.notunuzkaydedilmedi),Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete(View view) {

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",1);
        int artId = intent.getIntExtra("artId", 1);


        try {
            database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, date VARCHAR)");
            database.execSQL("DELETE FROM arts WHERE id == ?", new String[]{String.valueOf(position)});
            Toast.makeText(Main2Activity.this,this.getString(R.string.notunuzsilindi) ,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(Main2Activity.this,this.getString(R.string.notunuzsilinmedi),Toast.LENGTH_LONG).show();
        }
        Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }

    public void edit(View view) {
        String artName = artNameText.getText().toString();
        String painterName = painterNameText.getText().toString();
        Intent intent = getIntent();
        int artId = intent.getIntExtra("artId", 1);
        try {
            database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, date VARCHAR)");
            String sqlString = "UPDATE arts SET artname = ? , paintername = ?, date = ? WHERE id == ? ";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, artName);
            sqLiteStatement.bindString(2, painterName);
            sqLiteStatement.bindString(3, date);
            sqLiteStatement.bindLong(4, artId);
            sqLiteStatement.execute();
            Toast.makeText(Main2Activity.this,this.getString(R.string.notunuzduzenld),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(Main2Activity.this,this.getString(R.string.notunuzduzenmedi),Toast.LENGTH_LONG).show();
        }
        Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }


}






