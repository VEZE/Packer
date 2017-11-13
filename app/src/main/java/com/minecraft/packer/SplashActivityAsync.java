package com.minecraft.packer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;

import java.io.File;
import java.util.concurrent.ExecutionException;

import com.minecraft.packer.widget.DataAdapterAsync;
import com.minecraft.packer.widget.CrossPromoMain;

/**
 * Created by user on 9/22/2017.
 */

public class SplashActivityAsync extends Activity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    int SPLASH_DISPLAY_TIME = 2500;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(com.minecraft.packer.R.layout.splash_activity);
        Fabric.with(SplashActivityAsync.this, new Crashlytics());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActivityCompat.requestPermissions(SplashActivityAsync.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        ContextCompat.checkSelfPermission(SplashActivityAsync.this, // request permission when it is not granted.
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ContextCompat.checkSelfPermission(SplashActivityAsync.this, // request permission when it is not granted.
                Manifest.permission.READ_EXTERNAL_STORAGE);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6600624160903181~9348519130");

        if (android.os.Build.VERSION.SDK_INT <= 22) {
            startHeavyProcessing();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startHeavyProcessing();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SplashActivityAsync.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void startHeavyProcessing() {
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String

            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int currentVersion = 0;
            try {
                currentVersion = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
            } catch (final PackageManager.NameNotFoundException e) {
            }

            final int lastVersion = prefs.getInt("lastVersion_packer", -1);
            if (currentVersion > lastVersion) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName());
                if (dir.isDirectory())
                {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(dir, children[i]).delete();
                    }
                }
                SPLASH_DISPLAY_TIME = 0;
                DataAdapterAsync json = new DataAdapterAsync("pack", "games/com.mojang/" + SplashActivityAsync.this.getPackageName() + "/pack", SplashActivityAsync.this);
                json.doInBackground();
                prefs.edit().putInt("lastVersion_packer", currentVersion).commit();
            }

            File file=new File(Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName()+"/pack/","content.json");
            if (file.canRead()) {

            } else {
                SPLASH_DISPLAY_TIME = 0;
                DataAdapterAsync json = new DataAdapterAsync("pack", "games/com.mojang/" + SplashActivityAsync.this.getPackageName() + "/pack", SplashActivityAsync.this);
                json.doInBackground();
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("PackIntegrator", result);
            CrossPromoMain home = new CrossPromoMain(getApplicationContext().getPackageName());
            try {
                home.CrossPromoInit();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            new Handler().postDelayed(new Runnable() {
                public void run() {

                    Intent intent = new Intent(SplashActivityAsync.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();

                }
            }, SPLASH_DISPLAY_TIME);
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}

