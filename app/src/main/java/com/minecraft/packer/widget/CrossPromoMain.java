package com.minecraft.packer.widget;

/**
 * Created by user on 10/4/2017.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;

public class CrossPromoMain extends ListActivity {
         static String BundleName;
    /** Called when the activity is first created. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.fetchTwitterPublicTimeline()));

    }
    public CrossPromoMain(String BundleName){
        this.BundleName=BundleName;
    }
    public ArrayList<String> CrossPromoInit() throws ExecutionException, InterruptedException {
       // new fetchTwitterPublicTimeline().execute();
            return new fetchTwitterPublicTimeline().execute().get();
    }

  //  ArrayList<String>
    public static class  fetchTwitterPublicTimeline extends AsyncTask<Void, Void, ArrayList<String>>
    {
        public static ArrayList<String> getArrayll() throws ExecutionException, InterruptedException {
          //  new fetchTwitterPublicTimeline().execute().get();
            return listItems;
        }

        public static ArrayList<String> getImageList() throws ExecutionException, InterruptedException {
         //   new fetchTwitterPublicTimeline().execute().get();
            return ImageList;
        }
        public static ArrayList<String> getNameList() throws ExecutionException, InterruptedException {
            //   new fetchTwitterPublicTimeline().execute().get();
            return NameList;
        }
        String locale = Locale.getDefault().getLanguage();
       static ArrayList<String> listItems = new ArrayList<String>();
        static ArrayList<String> ImageList = new ArrayList<String>();
        static ArrayList<String> NameList=new ArrayList<String>();

        String line1="";


        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                URL twitter = new URL(
                        "http://minenetwork.site/json/"+BundleName+".json");
                URLConnection tc = twitter.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        tc.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    line1+=line;

                }
                in.close();
                JSONArray ja = new JSONArray(line1);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        listItems.add(jo.getString("bundle"));
                        ImageList.add("http://minenetwork.site/json/icons/"+jo.getString("bundle")+".png");

                        ArrayList<String> temp_array = new ArrayList<String>(Arrays.asList(jo.getString("title").replaceAll("[+^\"{}]", "").replace(",", ":").replace("&", ",").split(":")));
                        for (int k = 0; k < temp_array.size(); k++) {
                            if(temp_array.get(k).equals(locale)){
                                NameList.add(temp_array.get(k+1));

                            }

                        }

                    }


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return listItems;

        }

    }
}
