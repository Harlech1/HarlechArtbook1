package com.turkerkizilcik.artbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.vorlonsoft.android.rate.AppRate;
import com.vorlonsoft.android.rate.OnClickButtonListener;
import com.vorlonsoft.android.rate.StoreType;


public class MainActivity3 extends AppCompatActivity {

    EditText passwordText;
    SharedPreferences sharedPreferences;
    Button loginButton;
    Button signinButton;
    Button fakeButton;
    LinearLayout linearLayout;
    String secondapprove, secondreject, sifrebosbirak, hopsifreolmadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sharedPreferences = this.getSharedPreferences("com.turkerkizilcik.sifrelinotdefteri", Context.MODE_PRIVATE);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        signinButton = findViewById(R.id.signinButton);
        fakeButton = findViewById(R.id.fakeButton);
        linearLayout = findViewById(R.id.linearLayout);
        hopsifreolmadi = this.getString(R.string.hopsifreolmadi);
        sifrebosbirak = this.getString(R.string.sifrebosbirak);
        secondapprove = this.getString(R.string.fakeapprove);
        secondreject = this.getString(R.string.fakereject);


        AppRate.with(this)
                .setStoreType(StoreType.GOOGLEPLAY) //default is GOOGLEPLAY (Google Play), other options are
                //           AMAZON (Amazon Appstore) and
                //           SAMSUNG (Samsung Galaxy Apps)
                .setInstallDays((byte) 0) // default 10, 0 means install day
                .setLaunchTimes((byte) 3) // default 10
                .setRemindInterval((byte) 2) // default 1
                .setRemindLaunchTimes((byte) 2) // default 1 (each launch)
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                //Java 8+: .setOnClickButtonListener(which -> Log.d(MainActivity.class.getName(), Byte.toString(which)))
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(byte which) {
                        Log.d(MainActivity.class.getName(), Byte.toString(which));
                    }
                })
                .monitor();

        if (AppRate.with(this).getStoreType() == StoreType.GOOGLEPLAY) {
            //Check that Google Play is available
            if (GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(this) != ConnectionResult.SERVICE_MISSING) {
                // Show a dialog if meets conditions
                AppRate.showRateDialogIfMeetsConditions(this);
            }
        } else {
            // Show a dialog if meets conditions
            AppRate.showRateDialogIfMeetsConditions(this);
        }


        String storedPassword = sharedPreferences.getString("storedPassword", "a");
        String storedFakePassword = sharedPreferences.getString("storedFakePassword", "b");


        if (storedPassword != "a") {
            signinButton.setVisibility(View.INVISIBLE);
        } else {
            //loginButton.setVisibility(View.INVISIBLE);
        }
        if (storedFakePassword != "b") {
            fakeButton.setVisibility(View.INVISIBLE);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    @Override
    protected void onRestart() {
        passwordText.setText("");
        super.onRestart();
    }

    public void login(View view) {

        String password = passwordText.getText().toString();
        String storedFakePassword = sharedPreferences.getString("storedFakePassword", "password");
        String storedPassword = sharedPreferences.getString("storedPassword", "password");

        if (password.equals(storedPassword)) {
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
        } else if (password.equals(storedFakePassword)) {
            Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity3.this, this.getString(R.string.sifreeslesmedi), Toast.LENGTH_LONG).show();
        }
    }

    public void signin(View view) {

        final String password = passwordText.getText().toString();

        if (!password.matches("")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(this.getString(R.string.eminmisiniz));
            alert.setMessage(this.getString(R.string.sifrenizkaydedilio));
            alert.setNegativeButton(this.getString(R.string.evet), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sharedPreferences.edit().putString("storedPassword", password).apply();
                    signinButton.setVisibility(View.INVISIBLE);

                    AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity3.this);
                    alert2.setTitle(MainActivity3.this.getString(R.string.hosgeldin));
                    alert2.setMessage(MainActivity3.this.getString(R.string.mything));
                    alert2.setNegativeButton(MainActivity3.this.getString(R.string.evet), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            fakeButton.setVisibility(View.VISIBLE);
                        }
                    });
                    alert2.setPositiveButton(MainActivity3.this.getString(R.string.hayir), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loginButton.setVisibility(View.VISIBLE);
                        }
                    });

                    alert2.show();
                }
            });
            alert.setPositiveButton(this.getString(R.string.hayir), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity3.this, hopsifreolmadi, Toast.LENGTH_LONG).show();
                }
            });
            alert.show();
        } else {
            Toast.makeText(MainActivity3.this, sifrebosbirak, Toast.LENGTH_LONG).show();
        }
    }

    public void fake(View view) {

        String password = passwordText.getText().toString();
        String storedPassword = sharedPreferences.getString("storedPassword", "password");
        final String fakePassword = passwordText.getText().toString();
        final String storedFakePassword = sharedPreferences.getString("storedFakePassword", password);

        if (password.matches("")) {
            Toast.makeText(MainActivity3.this, this.getString(R.string.sahtesifreblank), Toast.LENGTH_LONG).show();
        } else if (fakePassword.matches(storedPassword)) {
            Toast.makeText(MainActivity3.this, this.getString(R.string.sahtesifregercek), Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alert3 = new AlertDialog.Builder(this);
            alert3.setTitle(this.getString(R.string.eminmisiniz));
            alert3.setMessage(this.getString(R.string.sahtesifreonaylama));
            alert3.setNegativeButton(this.getString(R.string.evet), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    sharedPreferences.edit().putString("storedFakePassword", storedFakePassword).apply();
                    Toast.makeText(MainActivity3.this, secondapprove, Toast.LENGTH_LONG).show();
                    loginButton.setVisibility(View.VISIBLE);
                    fakeButton.setVisibility(View.INVISIBLE);
                }
            });
            alert3.setPositiveButton(this.getString(R.string.hayir), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity3.this, secondreject, Toast.LENGTH_LONG).show();
                }
            });
            alert3.show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.change, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.pro) {
            try {
                Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.turkerkizilcik.sifrelinotdefteripro"));
                appStoreIntent.setPackage("com.android.vending");
                startActivity(appStoreIntent);
            } catch (ActivityNotFoundException exception) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.turkerkizilcik.artbook")));
            }
        }
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(getApplicationContext(), MainActivity8.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.change) {
            Intent intent = new Intent(getApplicationContext(), MainActivity6.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.pro1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}

