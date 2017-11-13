package com.minecraft.packer.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.minecraft.packer.GalleryWidget.Image;
import com.minecraft.packer.GalleryWidget.ImageName;
import com.minecraft.packer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 10/31/2017.
 */

public class CrossPromoTable extends Activity {

    Context context;
    TableLayout table;
    int height;
    int width;
    int temp_1;
    TextView myText;
    ArrayList<String> url;
    ArrayList<String> bundle;
    ArrayList<String> Name;
    ArrayList<String> name;


    public CrossPromoTable(Context context, int height) throws ExecutionException, InterruptedException {
        this.context = context;
        this.height = height;
        this.url = null;
        this.name=null;
        this.bundle = null;
        this.Name = null;
        temp_1 = 0;
        this.url = CrossPromoMain.fetchTwitterPublicTimeline.getImageList();
        this.bundle = CrossPromoMain.fetchTwitterPublicTimeline.getArrayll();
        this.name=CrossPromoMain.fetchTwitterPublicTimeline.getNameList();
       // this.Name = ;
    }


    public void init() {
        int temp = 0;





    }
    public static List<ImageName> loadNames(Context context){

         ArrayList<String> name=null;
        try {
            name=CrossPromoMain.fetchTwitterPublicTimeline.getNameList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
           // List<ImageName> imageNameList=new ArrayList<>();
        try{
            List<ImageName> imageNameList = new ArrayList<>();
            for(int i=1;i<name.size();i++){
               ImageName imageName=new ImageName();
                //= gson.fromJson(array.getString(i), Image.class);
                imageName.setImageName(name.get(i).toString());
                imageNameList.add(imageName);
            }
            return imageNameList;
        }catch (Exception e){
            Log.d("LoadImages","seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }


    }


    public static List<Image> loadImages(Context context){

        ArrayList<String> url = null;
        try {
            url = CrossPromoMain.fetchTwitterPublicTimeline.getImageList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            List<Image> imageList = new ArrayList<>();
            for(int i=1;i<url.size();i++){
                Image image = new Image();
                 //= gson.fromJson(array.getString(i), Image.class);
                image.setImageUrl(url.get(i).toString());
                imageList.add(image);
            }
            return imageList;
        }catch (Exception e){
            Log.d("LoadImages","seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }
    }
}
