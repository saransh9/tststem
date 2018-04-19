package com.tsystem.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by saransh on 19/04/18.
 */

public class Utility {
    public static int getScreenWidth(Context context) {

        //gets height and width but returns only width for use in aspect ratio
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.v("screen with utility", String.valueOf(width));
        return width;
    }
}
