package com.minecraft.packer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.minecraft.packer.widget.JSONadapterAsyncMain;
import com.onesignal.OneSignal;

import java.util.ArrayList;


/**
 * Created by user on 9/5/2017.
 */

public class MainActivity extends Activity {
    BitmapDrawable background;
    private AdView mAdView;
    private ProgressBar spinner;
    private ImageView mainImage;
    private ImageView firstLineImage;
    private ImageView secondLineImage;
    private ImageView thirdLineImage;
    private ImageView fourLineImage;
    private TextView firstLineText;
    private TextView secondLineText;
    private TextView thirdLineText;
    private TextView fourLineText;
    private Button mainButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.minecraft.packer.R.layout.activity_main);
        // MobileAds.initialize(this,"ca-app-pub-6600624160903181~9348519130");
        mainButton=(Button) findViewById(R.id.mainButton);
        firstLineImage =(ImageView) findViewById(R.id.firstLineImage);
        firstLineImage.setMinimumWidth(140);
        firstLineImage.setMinimumHeight(140);
        firstLineImage.setImageURI(Uri.parse( Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName() + "/" + "pack" + "/" + "main_0.png"));


        secondLineImage =(ImageView) findViewById(R.id.secondLineImage);
        secondLineImage.setMinimumWidth(140);
        secondLineImage.setMinimumHeight(140);
        secondLineImage.setImageURI(Uri.parse( Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName() + "/" + "pack" + "/" + "main_1.png"));
        thirdLineImage =(ImageView) findViewById(R.id.thirdLineImage);
        thirdLineImage.setMinimumWidth(140);
        thirdLineImage.setMinimumHeight(140);
        thirdLineImage.setImageURI(Uri.parse( Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName() + "/" + "pack" + "/" + "main_2.png"));
        fourLineImage =(ImageView) findViewById(R.id.fourLineImage);
        fourLineImage.setMinimumWidth(140);
        fourLineImage.setMinimumHeight(140);
        fourLineImage.setImageURI(Uri.parse( Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName() + "/" + "pack" + "/" + "main_3.png"));
        firstLineText =(TextView)findViewById(R.id.firstLineText) ;
        secondLineText =(TextView)findViewById(R.id.secondLineText) ;
        thirdLineText =(TextView)findViewById(R.id.thirdLineText) ;
        fourLineText =(TextView)findViewById(R.id.fourLineText) ;




        try {
            JSONadapterAsyncMain main_data = new JSONadapterAsyncMain(getApplicationContext());
            main_data.doInBackground();
            ArrayList<String> Texts = main_data.returnItems();
            firstLineText.setText(Texts.get(0));
            secondLineText.setText(Texts.get(1));
            thirdLineText.setText(Texts.get(2));
            fourLineText.setText(Texts.get(3));
            mainButton.setText(Texts.get(4));

        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            Toast.makeText(this, "Reinstall app or Contact us", Toast.LENGTH_SHORT).show();
        }


        mainImage =(ImageView) findViewById(R.id.main_backdrop);
        mainImage.setImageURI(Uri.parse( Environment.getExternalStorageDirectory() + "/games/com.mojang/" + getApplicationContext().getPackageName() + "/" + "pack" + "/" + "minecraft_logo.png"));
        background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), com.minecraft.packer.R.drawable.wiki_background));
        background.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
        View someView = findViewById(com.minecraft.packer.R.id.relativ_back);
        final CoordinatorLayout coordlay = findViewById(R.id.coordlay);
        View root = someView.getRootView();
        root.setBackground(background);
        coordlay.setVisibility(View.GONE);
        spinner = (ProgressBar) findViewById(R.id.progressBar2);
        spinner.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
       // spinner.setDrawingCacheBackgroundColor(ContextCompat.getColor(MainActivityTest.this, R.color.list_textcolor));
        Button button = findViewById(R.id.mainButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Intent intent = new Intent(view.getContext(), FragmentActivity.class);
                Intent intent = new Intent(view.getContext(), FragmentActivity.class);
                AdView adView = new AdView(view.getContext());
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId("ca-app-pub-6600624160903181/4096192456");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                onPause();
            }
        });

        spinner.setVisibility(View.VISIBLE);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        System.out.println("MainActivityTest.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                spinner.setVisibility(View.GONE);
                coordlay.setVisibility(View.VISIBLE);
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorcode) {
                spinner.setVisibility(View.GONE);
                coordlay.setVisibility(View.VISIBLE);
                mAdView.setVisibility(View.VISIBLE);

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        // download.CreateDownload("https://www.sunhome.ru/i/wallpapers/61/pozitivnie-kartinki.orig.jpg", this);
      //  TableLayout myTable = (TableLayout) findViewById(R.id.tableMenu);
      //  TextView myText = (TextView) findViewById(R.id.myText);

        //   CrossPromoTable cross= null;
        //  try {
        //       cross = new CrossPromoTable(this,myTable,myText,width);
        //  } catch (ExecutionException e) {
        //      e.printStackTrace();
        //  } catch (InterruptedException e) {
        //     e.printStackTrace();
        //  }
        //  cross.init();
    }
}

