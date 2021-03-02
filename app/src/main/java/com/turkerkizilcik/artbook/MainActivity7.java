package com.turkerkizilcik.artbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity7 extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText editText1;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        sharedPreferences = this.getSharedPreferences("com.turkerkizilcik.sifrelinotdefteri", Context.MODE_PRIVATE);
        editText1 = findViewById(R.id.EditTextSimdi);
        editText2 = findViewById(R.id.EditTextYeni);
        editText3 = findViewById(R.id.EditTextYeniConfirm);

    }

    public void saving (View view) {

        String storedPassword = sharedPreferences.getString("storedPassword", "password");
        String passwordsimdi = editText1.getText().toString();
        String passwordyeni = editText2.getText().toString();
        String passwordyeniconfirm = editText3.getText().toString();

        if(passwordsimdi.equals(storedPassword)) {
            if(!passwordsimdi.equals(passwordyeni)){
               if(passwordyeni.equals(passwordyeniconfirm)){
                       if(!passwordyeni.equals("")){
                           sharedPreferences.edit().putString("storedPassword", passwordyeni).apply();
                           Toast.makeText(this, this.getString(R.string.sifreeslesti), Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity7.this, MainActivity3.class);
                           startActivity(intent);
                       } else {
                           Toast.makeText(this, this.getString(R.string.sifreblankolamaz), Toast.LENGTH_SHORT).show();
                       }
               } else {
                   Toast.makeText(this, this.getString(R.string.confirmleyemedin), Toast.LENGTH_SHORT).show();
               }
            } else {
                Toast.makeText(this, this.getString(R.string.ayniolamaz), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, this.getString(R.string.eskisifreyanlis), Toast.LENGTH_SHORT).show();
        }


    }
}