package com.minecraft.packer.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by user on 9/22/2017.
 */

public class PackIntegratorAsync extends AsyncTask<String,String,String>{

    private File inputZipFile;
    private File unzipDir;
    private DataAdapterAsync data;
    JSONadapterAsync josneasy;
    ProgressDialog mProgressDialog;
    private Context context;
    private int page;
    DataAdapterAsync _data = new DataAdapterAsync();
    public void MadeJson1(Context context) {
      //  JSONadapterAsync josneasy1= new JSONadapterAsync(context);
       // josneasy1.doInBackground();
    }
    public PackIntegratorAsync(Context context,int Page) throws JSONException {
        this.inputZipFile = inputZipFile;
        this.unzipDir = unzipDir;
        JSONadapterAsync josneasy=new JSONadapterAsync(context);
        this.josneasy=josneasy;
        this.data=_data;
        this.context=context;
        this.page=Page;


    }

    @Override
    public String doInBackground(String... params) {

        josneasy.doInBackground();
        String random = josneasy.returningjson().toString();
        ArrayList<HashMap<String, String>> formListNew = josneasy.returningjson();

        String temp_mod_info = "";

        int temp=0;
        for (HashMap<String, String> hashMap : formListNew) {

            if(formListNew.get(temp).containsValue(page+".jpg")) {
            for (HashMap.Entry entry : hashMap.entrySet()) {
                if ((hashMap.containsValue("skin") == true)) {
                    if ((entry.getKey() == "filename")) {
                        try {
                            data.copyAssetFile(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/" + context.getPackageName() + "/" + "pack" + "/" + entry.getValue().toString(), Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/minecraftpe/" + "custom.png", context, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if ((hashMap.containsValue("mod") == true)) {
                    if ((entry.getKey() == "filename")) {
                        temp_mod_info = entry.getValue().toString();
                        temp_mod_info = temp_mod_info.replaceAll("[+^\"{}]", "").replace(",", ":");
                        ArrayList<String> temp_array = new ArrayList<String>(Arrays.asList(temp_mod_info.split(":")));
                        for (int k = 0; k < temp_array.size(); k++) {
                            if (temp_array.get(k).equals("0")) {
                                UnzipFileAsync unzip1 = new UnzipFileAsync(new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/" + context.getPackageName() + "/" + "pack" + "/" + temp_array.get(k + 1) + "/"), new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/behavior_packs/" + temp_array.get(k + 1).substring(0, temp_array.get(k + 1).length() - 7) + "/"));
                                unzip1.doInBackground();
                            }
                            if (temp_array.get(k).equals("1")) {
                                UnzipFileAsync unzip1 = new UnzipFileAsync(new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/" + context.getPackageName() + "/" + "pack" + "/" + temp_array.get(k + 1) + "/"), new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/resource_packs/" + temp_array.get(k + 1).substring(0, temp_array.get(k + 1).length() - 7) + "/"));
                                unzip1.doInBackground();

                            }

                        }
                        Log.d("Details-->", temp_array.toString());
                    }
                }

                   if ((hashMap.containsValue("map") == true)) {
                           if ((entry.getKey() == "filename")) {
                           temp_mod_info = entry.getValue().toString();
                           if (temp_mod_info.substring(temp_mod_info.length() - 4, temp_mod_info.length()).equals(".zip")) {
                               UnzipFileAsync unzip1 = new UnzipFileAsync(new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/" + context.getPackageName() + "/" + "pack" + "/" + temp_mod_info), new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/minecraftWorlds/" + temp_mod_info.substring(0, temp_mod_info.length() - 4) + "/"));
                               unzip1.doInBackground();

                           } else {
                               UnzipFileAsync unzip1 = new UnzipFileAsync(new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/" + context.getPackageName() + "/" + "pack" + "/" + temp_mod_info), new File(Environment.getExternalStorageDirectory().toString() + "/games/com.mojang/minecraftWorlds/" + temp_mod_info.substring(0, temp_mod_info.length() - 8) + "/"));
                               unzip1.doInBackground();
                           }
                       }
                   }
               }
            }
            temp=temp+1;
        }

        return random;

    }


    @Override
    protected void onPostExecute(String result) {
        Log.i("PackIntegrator" , result);
        mProgressDialog.dismiss();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }
}
