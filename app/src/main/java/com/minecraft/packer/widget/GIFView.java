package com.minecraft.packer.widget;

import java.io.InputStream;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.minecraft.packer.R;

public class GIFView extends View {
    public Movie mMovie;
    public long movieStart;


    public GIFView(Context context) {
        super(context);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    private void initializeView() {
//R.drawable.loader - our animated GIF
        InputStream is = getContext().getResources().openRawResource(+R.drawable.preload);
        mMovie = Movie.decodeStream(is);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        canvas.drawColor(Color.TRANSPARENT);
        canvas.scale(1f, 1f);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
           // mMovie.draw(canvas, getWidth()*1.05f - mMovie.width(), getHeight()/1.4f - mMovie.height());

            mMovie.draw(canvas, (getWidth()-mMovie.width())/2, (getHeight() - mMovie.height())/2);
            this.invalidate();
        }
    }
    private int gifId;

    public void setGIFResource(int resId) {
        this.gifId = resId;
        initializeView();
    }

    public int getGIFResource() {
        return this.gifId;
    }
}