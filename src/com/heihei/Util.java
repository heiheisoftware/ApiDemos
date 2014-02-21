
package com.heihei;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {

    public static boolean isSimReady(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) 
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivity.getActiveNetworkInfo() != null;
    }

    public static void hideKeyBoard(Context context, View view) {
        if (context == null || view == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyBoard(Context context, View view) {
        if (context == null || view == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }
    
    /**
     * 
     * @param file
     * @param reqWidth 解析得到的图片宽度
     * @param reqHeight 解析得到的图片高度
     * @return
     */
    public static Bitmap decodeBitmapInSample(String file, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        
        Bitmap b = BitmapFactory.decodeFile(file, options);
        return b;
    }
    
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
