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
import java.util.Calendar;

public class MainActivity4 extends AppCompatActivity {

    EditText artNameText, painterNameText;
    Button button;
    Button button2;
    Button button3;
    LinearLayout linearLayout3;
    SQLiteDatabase database;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        artNameText = findViewById(R.id.artNameText);
        painterNameText = findViewById(R.id.painterNameText);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        database = this.openOrCreateDatabase("FakeArts", MODE_PRIVATE, null);
        linearLayout3 = findViewById(R.id.linearLayout3);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        String info1 = intent.getStringExtra("date");

        if (info.matches("delete")) {
            button.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = linearLayout3.getLayoutParams();
            params.width = 1200;
            try {
                int artId = intent.getIntExtra("artId", 1);
                // id si işte main1 deki onClick metodundan geliyo sonra onun artname + paintername'sini alıyo sonra işte onları yerine koyuyo kapıyo
                Cursor cursor = database.rawQuery("SELECT * FROM fakearts WHERE id == ?", new String[]{String.valueOf(artId)});
                int artNameIx = cursor.getColumnIndex("artname");
                int painterNameIx = cursor.getColumnIndex("paintername");
                int dateIx = cursor.getColumnIndex("date");
                intent.getStringExtra("date");
                //textView.setText(info1);


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

            int artId = intent.getIntExtra("artId", 1);
            button.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = linearLayout3.getLayoutParams();
            params.width = 1200;
            try {

                // id si işte main1 deki onClick metodundan geliyo sonra onun artname + paintername'sini alıyo sonra işte onları yerine koyuyo kapıyo
                Cursor cursor = database.rawQuery("SELECT * FROM fakearts WHERE id == ?", new String[]{String.valueOf(artId)});
                int artNameIx = cursor.getColumnIndex("artname");
                int painterNameIx = cursor.getColumnIndex("paintername");
                int dateIx = cursor.getColumnIndex("date");
                intent.getStringExtra("date");
                //textView.setText(info1);


                while (cursor.moveToNext()) {
                    artNameText.setText(cursor.getString(artNameIx));
                    painterNameText.setText(cursor.getString(painterNameIx));
                    //currentTime = cursor.getString(dateIx);
                }
                cursor.close();
            } catch (Exception e) {
            }
        }
    }

    public void save(View view) {

        String artName = artNameText.getText().toString();
        String painterName = painterNameText.getText().toString();
        String currentTime = Calendar.getInstance().getTime().toString();

        try {
            //arts tablesinin içine işte yazılanları koyuyo artName ve painterName olarak
            database = this.openOrCreateDatabase("FakeArts", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS fakearts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, date VARCHAR)");
            String sqlString = "INSERT INTO fakearts(artname, paintername, date) VALUES (?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, artName);
            sqLiteStatement.bindString(2, painterName);
            sqLiteStatement.bindString(3, currentTime);
            sqLiteStatement.execute();

        } catch (Exception e) {
        }
        // sonra yeni main1 e atıyo intent başlıyo
        Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void delete(View view) {

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",1);
        int artId = intent.getIntExtra("artId", 1);


        try {
            database = this.openOrCreateDatabase("FakeArts", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS fakearts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, date VARCHAR)");
            database.execSQL("DELETE FROM fakearts WHERE id == ?", new String[]{String.valueOf(position)});
        } catch (Exception e) {
        }
        Intent intent1 = new Intent(MainActivity4.this, MainActivity5.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }

    public void edit(View view) {
        String currentTime = Calendar.getInstance().getTime().toString();
        String artName = artNameText.getText().toString();
        String painterName = painterNameText.getText().toString();
        Intent intent = getIntent();
        int artId = intent.getIntExtra("artId", 1);
        try {
            database = this.openOrCreateDatabase("FakeArts", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS fakearts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, date VARCHAR)");
            database.execSQL("DELETE FROM fakearts WHERE id == ?", new String[]{String.valueOf(artId)});
            String sqlString = "INSERT INTO fakearts(artname, paintername,date) VALUES (?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, artName);
            sqLiteStatement.bindString(2, painterName);
            sqLiteStatement.bindString(3, currentTime);
            sqLiteStatement.execute();

        } catch (Exception e) {
        }
        Intent intent1 = new Intent(MainActivity4.this, MainActivity5.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }
}