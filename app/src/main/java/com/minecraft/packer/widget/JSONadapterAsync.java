package com.minecraft.packer.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

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
import java.util.HashMap;

/**
 * Created by user on 9/22/2017.
 */

public class JSONadapterAsync extends AsyncTask<String, String, String> {
    private Context context;

    String _ItemName = "";
    int temp_number;
    ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> formListString = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> formListMain = new ArrayList<HashMap<String, String>>();

    public ArrayList<HashMap<String, String>> returningjson() {
        return formList;
    }

    public ArrayList<HashMap<String, String>> returningjsonstring() {
        return formListString;
    }

    public JSONadapterAsync(Context context) {
        this.context = context;

    }
    public String number_list(){
        return this._ItemName;
    }

    @Override
    public String doInBackground(String... params) {


        try {
            InputStream inputStream = null;
            String result = null;
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
            JSONArray m_jArry = obj.getJSONArray("content");
            JSONArray m_jArryString = obj.getJSONArray("string");


            HashMap<String, String> m_li;
            HashMap<String, String> m_li1;
            for (int i = 0; i < m_jArryString.length(); i++) {

                JSONObject jo_inside = m_jArryString.getJSONObject(i);

                String desc = jo_inside.getString("desc");
                String imgname = jo_inside.getString("imgname");
                String name = jo_inside.getString("name");
                //Add your values in your `ArrayList` as below:

                m_li1 = new HashMap<String, String>();
                m_li1.put("desc", desc);
                m_li1.put("imgname", imgname);
                m_li1.put("name", name);

                formListString.add(m_li1);
            }

             temp_number=formListString.size();
            for (HashMap<String, String> hashMap : formListString) {
                System.out.println(hashMap.keySet());
                for (String key : hashMap.keySet()) {
                    _ItemName += "\n" + hashMap.get(key);
                    if (hashMap.get(key).substring(hashMap.get(key).length() - 3, hashMap.get(key).length()).equals(".jpg")) {

                    }

                }


            }
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);


                String type = jo_inside.getString("type");
                String imgname = jo_inside.getString("imgname");
                String filename = jo_inside.getString("filename");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("type", type);
                m_li.put("imgname", imgname);
                m_li.put("filename", filename);

                formList.add(m_li);
            }


            if (fileInStream != null) {
                fileInStream.close();
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
    protected void onPostExecute(String result) {
        Log.i("PackIntegrator", result);

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }


}
