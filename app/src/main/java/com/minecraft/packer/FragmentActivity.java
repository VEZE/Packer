package com.minecraft.packer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.minecraft.packer.Fragments.Fragment_1;
import com.minecraft.packer.widget.PackIntegratorAsync;
import com.viewpagerindicator.LinePageIndicator;

import org.json.JSONException;

import java.util.Random;

public class FragmentActivity extends AppCompatActivity implements RewardedVideoAdListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static final String TAG = "myLogs";
    static final String SAVE_PAGE_NUMBER = "save_page_number";
    static int i_count;
    static int ITEMS = 9;
    ProgressDialog progress;
    int pageNumber;
    AdView nAdView;
    int progress_status;
    InterstitialAd mInterstitialAd;
    CoordinatorLayout fragment_main;
    TextView text;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private RewardedVideoAd mAd;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.minecraft.packer.R.layout.fragment_activity_main);
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        i_count = mPrefs.getInt("i_count", 1);

        MobileAds.initialize(this, "ca-app-pub-6600624160903181~9348519130");
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        //loadRewardedVideoAd();
        mAd.loadAd("ca-app-pub-6600624160903181/3661078183",
                new AdRequest.Builder().build());
        nAdView = (AdView) findViewById(R.id.nadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        nAdView.loadAd(adRequest);
        fragment_main = findViewById(R.id.main_content);
        spinner = (ProgressBar) findViewById(R.id.progressBar3);
        spinner.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.VISIBLE);
        fragment_main.setVisibility(View.GONE);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(com.minecraft.packer.R.id.container);

        mViewPager.setOffscreenPageLimit(ITEMS);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        LinePageIndicator lineindicator = (LinePageIndicator) findViewById(com.minecraft.packer.R.id.indicator);
        lineindicator.setLineWidth(width / ITEMS);
        lineindicator.setScaleY(4);
        lineindicator.setCentered(true);
        lineindicator.setPadding(0, 5, 0, 0);
        lineindicator.setSelectedColor(ContextCompat.getColor(this, R.color.button_download));
        lineindicator.setViewPager(mViewPager);
        ImageButton imgbut = (ImageButton) findViewById(R.id.imageButton);
        text = (TextView) findViewById(R.id.imageText);
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad));

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        nAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                spinner.setVisibility(View.GONE);
                fragment_main.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdLoaded() {
                spinner.setVisibility(View.GONE);
                fragment_main.setVisibility(View.VISIBLE);
            }

        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                fragment_main.setVisibility(View.GONE);
                if(mViewPager.getCurrentItem()+1 == ITEMS){
               //     spinner.setVisibility(View.GONE);
                    //fragment_main.setVisibility(View.VISIBLE);
                    final Dialog dialog1 = new Dialog(FragmentActivity.this);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.custom_dialog_last);
                    dialog1.setCancelable(false);
                    final Button buttonyeslast = (Button) dialog1.findViewById(R.id.buttonyeslast);
                    final RatingBar rating = (RatingBar) dialog1.findViewById(R.id.ratingBar);
                }else {
                    spinner.setVisibility(View.VISIBLE);
                    fragment_main.setVisibility(View.GONE);
                    if (mAd.isLoaded()) {

                        mAd.show();

                        spinner.setVisibility(View.GONE);
                        fragment_main.setVisibility(View.VISIBLE);
                        mSectionsPagerAdapter.notifyDataSetChanged();
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mAd.isLoaded()) {
                                    onStop();
                                    mAd.show();
                                } else {

                                    if (mInterstitialAd.isLoaded()) {
                                        spinner.setVisibility(View.GONE);
                                        fragment_main.setVisibility(View.VISIBLE);
                                        mInterstitialAd.show();
                                        onRewarded(null);
                                    } else {
                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                        spinner.setVisibility(View.GONE);
                                        fragment_main.setVisibility(View.VISIBLE);
                                        onRewarded(null);

                                    }
                                }
                            }
                        }, 12000);

                    }
                    if (isNetworkAvailable()) {
                        mAd.loadAd("ca-app-pub-6600624160903181/3661078183",
                                new AdRequest.Builder().build());
                    }
                }
            }

        });


//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                final ProgressDialog prog = new ProgressDialog(view.getContext());
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                } else {
//                    Log.d("TAG", "The interstitial wasn't loaded yet.");
//                }
//                mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdClosed() {
//                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//
//                        new AsyncTask<Void, Integer, Void>() {
//
//                            @Override
//                            protected void onPostExecute(Void Result) {
//                                super.onPostExecute(Result);
//                                prog.dismiss();
//
//                                i_count++;
//                                if (i_count >= ITEMS) {
//                                    i_count = ITEMS;
//                                }
//                                mSectionsPagerAdapter.notifyDataSetChanged();
//                                CustomDialog custom = new CustomDialog();
//                                if (custom.ShowDialog(view.getContext(), "text") == Boolean.TRUE) {
//                                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//                                }
//                                mSectionsPagerAdapter.notifyDataSetChanged();
//
//
//                            }
//
//                            @Override
//                            protected void onProgressUpdate(Integer... values) {
//                                super.onProgressUpdate(values);
//
//                                prog.setProgress(values[0]);
//
//                            }
//
//                            @Override
//                            protected void onPreExecute() {
//
//
//                                super.onPreExecute();
//                                progress_status = 0;
//
//
//                                prog.setTitle("Intergrating...");
//
//                                prog.setIndeterminate(false);
//                                prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                                prog.show();
//                            }
//
//                            @Override
//                            protected Void doInBackground(Void... params) {
//                                //    SystemClock.sleep(6000);
//
//                                while (progress_status < 100) {
//
//                                    progress_status += 1;
//
//                                    publishProgress(progress_status);
//                                    SystemClock.sleep(new Random().nextInt(100) + 50);
//
//                                    if (progress_status == 50) {
//                                        PackIntegratorAsync integrasync = null;
//                                        try {
//                                            integrasync = new PackIntegratorAsync(view.getContext(), mViewPager.getCurrentItem());
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        integrasync.doInBackground();
//                                    }
//
//                                }
//                                return null;
//                            }
//                        }.execute();
//
//                    }
//                });
//
//
//            }
//        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-6600624160903181/3661078183",
                new AdRequest.Builder().build());

    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //      spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        fragment_main.setVisibility(View.VISIBLE);
        // fragment_main.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        mAd.loadAd("ca-app-pub-6600624160903181/3661078183",
                new AdRequest.Builder().build());
        // Toast.makeText(FragmentActivity.this, "Watch video and we will open next step", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        if (mViewPager.getCurrentItem()+1 == ITEMS) {
            final Dialog dialog1 = new Dialog(FragmentActivity.this);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(R.layout.custom_dialog_last);
            dialog1.setCancelable(false);
            final Button buttonyeslast = (Button) dialog1.findViewById(R.id.buttonyeslast);
            final RatingBar rating = (RatingBar) dialog1.findViewById(R.id.ratingBar);
        } else {
            final ProgressDialog prog = new ProgressDialog(this);
            new AsyncTask<Void, Integer, Void>() {
                @Override
                protected void onPostExecute(Void Result) {
                    super.onPostExecute(Result);

                    mAd.loadAd("ca-app-pub-6600624160903181/3661078183",
                            new AdRequest.Builder().build());
                    final Dialog dl = new Dialog(FragmentActivity.this);
                    dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dl.setContentView(R.layout.custom_dialog);
                    dl.setCancelable(false);
                    dl.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (mViewPager.getCurrentItem() % 2 == 0) {
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                }
                            }
                        }
                    });
                    prog.dismiss();
                    TextView tv_message = (TextView) dl.findViewById(R.id.textViewMessagelast);
                    tv_message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    Button bt_yes = (Button) dl.findViewById(R.id.buttonyes);
                    Button bt_no = (Button) dl.findViewById(R.id.buttonnolast);
                    bt_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSectionsPagerAdapter.notifyDataSetChanged();
                            i_count = mViewPager.getCurrentItem() + 2;
                            SharedPreferences mPrefs = getSharedPreferences("label", 0);
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putInt("i_count", i_count).commit();
                            dl.dismiss();
                            if (i_count >= ITEMS) {
                                i_count = ITEMS;
                                final Dialog dialog1 = new Dialog(FragmentActivity.this);
                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog1.setContentView(R.layout.custom_dialog_last);
                                dialog1.setCancelable(false);
                                final Button buttonyeslast = (Button) dialog1.findViewById(R.id.buttonyeslast);
                                final RatingBar rating = (RatingBar) dialog1.findViewById(R.id.ratingBar);

                                rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, final float rating, boolean b) {
                                        buttonyeslast.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (rating <= 3) {
                                                    Intent Email = new Intent(Intent.ACTION_SEND);
                                                    Email.setType("text/email");
                                                    Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"mineappdev@gmail.com"});
                                                    Email.putExtra(Intent.EXTRA_SUBJECT, FragmentActivity.this.getPackageName());
                                                    Email.putExtra(Intent.EXTRA_TEXT, "");
                                                    startActivity(Intent.createChooser(Email, "Send Feedback:"));
                                                    dialog1.dismiss();
                                                } else {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + FragmentActivity.this.getPackageName())));
                                                    dialog1.dismiss();
                                                }
                                            }
                                        });
                                    }

                                });
                                dialog1.show();
                            }
                            mSectionsPagerAdapter.notifyDataSetChanged();
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);


                            mSectionsPagerAdapter.notifyDataSetChanged();
                        }
                    });
                    bt_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dl.dismiss();
                        }

                    });
                    dl.show();
//                                if ( custom.showYes()== true) {
//                                    mSectionsPagerAdapter.notifyDataSetChanged();
//                                    i_count=mViewPager.getCurrentItem()+1;
//                                    if (i_count >= ITEMS) {
//                                        i_count = ITEMS;
//                                        Toast.makeText(FragmentActivity.this,"kypi plz",Toast.LENGTH_LONG).show();
//
//                                    }
//                                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//                                }
//
//
//                   mSectionsPagerAdapter.notifyDataSetChanged();

                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    prog.setProgress(values[0]);
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progress_status = 0;
                    prog.setCancelable(false);
                    prog.setTitle(getResources().getString(R.string.dialog_text_top));
                    prog.setIndeterminate(false);
                    prog.setProgressDrawable(ContextCompat.getDrawable(FragmentActivity.this, R.drawable.drawable_dialog));
                    prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    if(!isFinishing()){
                        prog.show();
                    }

                }

                @Override
                protected Void doInBackground(Void... params) {
                    while (progress_status < 100) {
                        progress_status += 1;
                        publishProgress(progress_status);
                        SystemClock.sleep(new Random().nextInt(100) + 50);
                        if (progress_status == 50) {
                            PackIntegratorAsync integrasync = null;
                            try {
                                integrasync = new PackIntegratorAsync(FragmentActivity.this, mViewPager.getCurrentItem());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            integrasync.doInBackground();
                        }
                    }
                    return null;
                }
            }.execute();
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Turn on Internet and Restart App", Toast.LENGTH_LONG).show();
        }
//        if (isNetworkAvailable() || i == 3) {
//        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            try {
                fragment=Fragment_1.newInstance(position, position + 1, ITEMS);
            }catch (IndexOutOfBoundsException e){
                Toast.makeText(getApplicationContext(),"Please free your disk space",Toast.LENGTH_LONG).show();
            }
            return fragment;
        }

        @Override
        public int getCount() {


            return i_count;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
