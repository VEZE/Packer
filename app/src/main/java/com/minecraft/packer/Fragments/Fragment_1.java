package com.minecraft.packer.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;
import com.minecraft.packer.GalleryWidget.Image;
import com.minecraft.packer.GalleryWidget.ImageName;
import com.minecraft.packer.R;
import com.minecraft.packer.SplashActivityAsync;
import com.minecraft.packer.gallery.ImageTypeBig;
import com.minecraft.packer.gallery.ImageTypeSmallList;
import com.minecraft.packer.widget.CrossPromoTable;
import com.minecraft.packer.widget.DataAdapterAsync;
import com.minecraft.packer.widget.ExpandableList;
import com.minecraft.packer.widget.ImageAdapter;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/7/2017.
 */

public class Fragment_1 extends Fragment {
    ArrayList<String> tempo;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    // Store instance variables
    private int page;
    private int page1;
    private int pagemax;
    private PlaceHolderView mGalleryView;

    // newInstance constructor for creating fragment with arguments
    public static Fragment_1 newInstance(int page, int someint, int someintnext) {
        Fragment_1 fragmentFirst = new Fragment_1();

        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putInt("someInteger", someint);
        args.putInt("someintnext", someintnext);


        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        page = getArguments().getInt("someInt", 0);
        page1 = getArguments().getInt("someInteger", 0);
        pagemax = getArguments().getInt("someintnext", 0);

    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (page == pagemax - 1) {
            View view = inflater.inflate(R.layout.fragment_rate, container, false);
            mGalleryView = (PlaceHolderView) view.findViewById(R.id.galleryView);
            setupGallery();

//                View view = inflater.inflate(R.layout.fragment_rate, container, false);
//
//                TableLayout myTable = (TableLayout) view.findViewById(R.id.tableMenu);
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int height = displayMetrics.heightPixels;
//                int width = displayMetrics.widthPixels;
//                TextView myText=(TextView)view.findViewById(R.id.myText);
//
//                   CrossPromoTable cross= null;
//                try {
//                    cross = new CrossPromoTable(this.getContext(),myTable,myText,width,height);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                cross.init();


            return view;
        } else {

            View view = inflater.inflate(R.layout.fragment_adapter, container, false);
            ExpandableList zxc1 = null;
            try {
                zxc1 = new ExpandableList(this.getContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            zxc1.smthdo();

            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_set);
            ImageAdapter adapter = null; //Here we are defining the Imageadapter object

            try {
                tempo = zxc1.stringoo();

                adapter = new ImageAdapter(this.getContext(), tempo);
                adapter.isViewFromObject(view, imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
            imageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/games/com.mojang/" + this.getContext().getPackageName() + tempo.get(page1 - 1)));
            expListView = (ExpandableListView) view.findViewById(R.id.lvExp);



                listAdapter = new com.minecraft.packer.widget.ExpandableListAdapter(this.getContext(), zxc1.Start(page), zxc1.start1());
            } catch (IndexOutOfBoundsException e) {

                DataAdapterAsync json = new DataAdapterAsync("pack", "games/com.mojang/" + getContext().getPackageName() + "/pack", getContext());
                json.execute();
                Toast.makeText(this.getContext(), "something gone wrong,Restart pls App", Toast.LENGTH_LONG).show();
            }
            expListView.setAdapter(listAdapter);

            return view;
        }


    }
    private void setupGallery(){

       // List<Image> imageList = Utils.loadImages(this.getContext());
        List<Image> imageList= CrossPromoTable.loadImages(this.getContext());
        List<Image> newImageList = new ArrayList<>();
        List<ImageName> imageNameList= CrossPromoTable.loadNames(this.getContext());
        for (int i = 0; i <  (imageList.size() > 10 ? 10 : imageList.size()); i++) {
            newImageList.add(imageList.get(i));
        }
        mGalleryView.addView(new ImageTypeSmallList(this.getContext(), newImageList));
        mGalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        try {
            for (int i = imageList.size() - 1; i >= 0; i--) {
                mGalleryView.addView(new ImageTypeBig(this.getContext(), mGalleryView, imageList.get(i).getImageUrl(), imageNameList.get(i).getImageName()));

            }
        }catch (IndexOutOfBoundsException e){
            Toast.makeText(getContext(),"Something gone wrong",Toast.LENGTH_LONG).show();
        }

//        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mGalleryView.removeAllViews();
//            }
//        });
    }

}
