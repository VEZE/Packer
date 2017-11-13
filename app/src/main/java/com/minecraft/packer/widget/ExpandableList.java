package com.minecraft.packer.widget;

import android.app.Activity;
import android.content.Context;
import android.widget.ExpandableListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by user on 9/18/2017.
 */

public class ExpandableList extends Activity {

    ArrayList<String> listDataHeader;
    ArrayList<String> stringo;
    HashMap<String, String> listDataChild;
    Context context;


    public ExpandableList(Context context) throws JSONException {
        this.context = context;

    }
     public void smthdo(){
         prepareListData();

     }

    public HashMap<String, String> start1() {
        return this.listDataChild;
    }

    public String Start(int position) {
        return this.listDataHeader.get(position);

    }
    public ArrayList<String> stringoo(){return this.stringo;}


    private void prepareListData() {

        JSONadapterAsync josneasy = new JSONadapterAsync(context);
        josneasy.doInBackground();
        String temp = "";
        int temp1 = 0;
        String temp_desc="";
        ArrayList<HashMap<String, String>> formListNew = josneasy.returningjsonstring();
        ArrayList<String> temping = new ArrayList<String>();
        listDataChild = new HashMap<String, String>();
        listDataHeader = new ArrayList<String>();
        stringo = new ArrayList<String>();
        String locale = Locale.getDefault().getLanguage();
        for (HashMap<String, String> hashMap : formListNew) {
            for (HashMap.Entry entry : hashMap.entrySet()) {
                if (entry.getKey() == "imgname") {
                    temp = entry.getValue().toString();
                    temp = temp.substring(0, temp.length() - 4);
                    temp1 = Integer.parseInt(temp);
                    stringo.add("/"+"pack"+"/"+entry.getValue().toString());
                }
                if (entry.getKey() == ("name")) {
                    String temp_name =entry.getValue().toString().replaceAll("[+^\"{}]", "");

                        ArrayList<String> temp_array = new ArrayList<String>(Arrays.asList(entry.getValue().toString().replaceAll("[+^\"{}]", "").replace(",", ":").replace("&", ",").split(":")));
                    for (int k = 0; k < temp_array.size(); k++) {
                        if(temp_array.get(k).equals(locale)){
                            listDataHeader.add(temp_array.get(k+1));

                        }

                    }
                }
                if (entry.getKey() == ("desc")) {
                   // temp_desc=entry.getValue().toString();

                    String temp2 = entry.getValue().toString().replaceAll("[+^\"{}]", "");
                    ArrayList<String> temp_array = new ArrayList<String>(Arrays.asList(entry.getValue().toString().replaceAll("[+^\"{}]", "").replace(",", ":").replace("&",",").split(":")));
                    for (int k = 0; k < temp_array.size(); k++) {
                        if(temp_array.get(k).equals(locale)){

                            temping.add(temp1, temp_array.get(k+1).replace("#",":"));
                        }

                    }


                }

            }
        }
        for (int i = 0; i < temping.size(); i++) {
            listDataChild.put(listDataHeader.get(i), temping.get(i));
        }

    }
}