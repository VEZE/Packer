package com.minecraft.packer.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by user on 9/15/2017.
 */

public class ImageAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> tempono;

    public ImageAdapter(Context context,ArrayList<String> tempoo) throws FileNotFoundException, JSONException {
        this.tempono= tempoo;
        this.context=context;

    }
    @Override
    public int getCount() {

        return tempono.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((ImageView) object);

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

            imageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/games/com.mojang/" + context.getPackageName()+tempono.get(position)));
            container.addView(imageView);
        return imageView;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView((ImageView) object);

    }

}
