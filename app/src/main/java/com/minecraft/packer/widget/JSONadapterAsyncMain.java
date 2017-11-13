package com.minecraft.packer.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by user on 11/2/2017.
 */

public class JSONadapterAsyncMain extends AsyncTask<Void, Void, Void> {
    Context context;


    int temp_number;
    ArrayList<String> temping = new ArrayList<String>();
    private ArrayList<HashMap<String, String>> formListMain = new ArrayList<HashMap<String, String>>();

    public JSONadapterAsyncMain(Context context) {
        this.context = context;


    }

    public ArrayList<String> returnItems() {
        return temping;
    }

    @Override
    public Void doInBackground(Void... params) {


        try {
            InputStream inputStream = null;
            String result = null;

            int temp;
            String locale;
            BufferedInputStream fileInStream = new BufferedInputStream(new FileInputStream(Environment.getExternalStorageDirectory() + "/games/com.mojang/" + context.getPackageName() + "/" + "pack" + "/" + "content.json"));

            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
            reader.close();
            result = sb.toString();

            JSONObject obj = new JSONObject(result);
            JSONArray m_jArryMain = obj.getJSONArray("main");

            HashMap<String, String> m_li_main;

            for (int i = 0; i < m_jArryMain.length(); i++) {

                JSONObject jo_inside = m_jArryMain.getJSONObject(i);
                String desc = jo_inside.getString("desc");
                String imgname = jo_inside.getString("id");
                //Add your values in your `ArrayList` as below:
                m_li_main = new HashMap<String, String>();
                m_li_main.put("desc", desc);
                m_li_main.put("id", imgname);
                formListMain.add(m_li_main);

            }
            if (fileInStream != null) {
                fileInStream.close();
            }

            temping = new ArrayList<String>();
            temp = 0;
            locale = Locale.getDefault().getLanguage();
            for (HashMap<String, String> hashMap : formListMain) {
                for (HashMap.Entry entry : hashMap.entrySet()) {
                    if (entry.getKey() == "id") {
                        temp = Integer.parseInt((String) entry.getValue());
                    }

                    if (entry.getKey() == ("desc")) {

                        String temp2 = entry.getValue().toString().replaceAll("[+^\"{}]", "");
                        ArrayList<String> temp_array = new ArrayList<String>(Arrays.asList(entry.getValue().toString().replaceAll("[+^\"{}]", "").replace(",", ":").replace("&", ",").split(":")));
                        for (int k = 0; k < temp_array.size(); k++) {
                            if (temp_array.get(k).equals(locale)) {

                                temping.add(temp, temp_array.get(k + 1).replace("#", ":"));
                            }

                        }


                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


}

