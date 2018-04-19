package com.tsystem.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import com.tsystem.R;

/**
 * Created by saransh on 19/04/17.
 */

public class RatioImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "RatioImageView";

    private static final int FIXED_WIDTH = 0;
    private static final int FIXED_HEIGHT = 1;

    private static final int DEFAULT_FIXED_DIMENSION = FIXED_WIDTH;
    private static final float DEFAULT_ASPECT_RATIO = 1.0f;

    // private ImageInfo mImageInfo;
    private int height, width, newheight=4, newwidth=4;
    private int mFixedDimension;
    private ImageListener imageListener;
    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mFixedDimension = a.getInt(R.styleable.RatioImageView_fixed_dimension,
                DEFAULT_FIXED_DIMENSION);
        a.recycle();
    }

    public void setImageInfo(int height, int width) {
        this.height = height;
        this.width = width;
        requestLayout();
    }
    public void setImageInfo(int height, int width, ImageListener imageListener)
    {
        this.height = height;
        this.width = width;
        this.imageListener=imageListener;
        requestLayout();
    }

    public int getNewHeight() {
        return this.newheight;
    }

    public int getNewWidth() {
        return this.newwidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Get aspect ratio from ImageInfo
        float aspectRatio = DEFAULT_ASPECT_RATIO;
        if (height != 0 && width != 0) {
            aspectRatio = ((float) width / height); // w : h
        }

        // Set dimensions according to aspect ratio
        if (mFixedDimension == FIXED_WIDTH) {
            int width = getMeasuredWidth();
            int height = (int) (width / aspectRatio);
           // Log.v("FIXED_WIDTH","here");
            this.newheight = height;
            this.newwidth = width;

            setMeasuredDimension(width, height);
        } else if (mFixedDimension == FIXED_HEIGHT) {
            int height = getMeasuredHeight();
            int width = (int) (height * aspectRatio);
            //Log.v("FIXED_HEIGHT","here " + width);
            this.newheight = height;
            this.newwidth = width;
            imageListener.OnDimensionSet(height,width);

            setMeasuredDimension(width, height);
        } else {
            Log.w(TAG, "Invalid fixed_dimension attribute, must be 'width' or 'height'.");
        }
    }

}
