package com.minecraft.packer.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.minecraft.packer.R;
import com.squareup.picasso.Picasso;


/**
 * Created by janisharali on 19/08/16.
 */
@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.gallery_item_big)
public class ImageTypeBig {

    @View(R.id.imageView)
    private ImageView imageView;
    @View(R.id.textView)
    private TextView textView;

    private String mUlr;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;
    private String mName;

    public ImageTypeBig(Context context, PlaceHolderView placeHolderView, String ulr,String name) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUlr = ulr;
        mName=name;
    }

    @Resolve
    private void onResolved() {
      //  Glide.with(mContext).load(mUlr).into(imageView);
        Picasso.with(mContext).load(mUlr).into(imageView);
        textView.setText(mName);
    }

//    @LongClick(R.id.imageView)
//    private void onLongClick(){
//        mPlaceHolderView.removeView(this);
//    }
    @Click(R.id.imageView)
    private void Click(){
        // Toast.makeText(mContext,mUlr,Toast.LENGTH_LONG).show();
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +mUlr.substring(35, mUlr.length()-4))));

    }
}
