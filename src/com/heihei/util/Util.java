
package com.heihei.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.InputMethodManager;

import com.example.android.apis.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 编码解码库（md5 base64等）http://commons.apache.org/proper/commons-codec/
 * @author linqing
 *
 */
public class Util {

    /**
     * sim卡是不是可用
     * @param context
     * @return true sim卡可用，否则不可用
     */
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
     * 构造view的缓存生成一个Bitmap
     * @param v
     * @return
     */
    public static Bitmap buildCacheBitmap(View v) {
        v.setDrawingCacheEnabled(true);

        // this is the important code :)  
        // Without it the view will have a dimension of 0,0 and the bitmap will be null          
        v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                     MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
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

    public static void installApkCheckMd5(Context c, File file, String md5Str) {
        //      try {
        //      if (TextUtils.isEmpty(md5Str) || !md5Str.equals(MD5Util.md5Hex(savedFile))) {
        //          Util.showToast(c, R.string.upgrade_file_check);
        //          return;
        //      }
        //  } catch (IOException e) {
        //      e.printStackTrace();
        //      Util.showToast(c, R.string.upgrade_file_check);
        //      return;
        //  }
        installApk(c, file);
    }

    /**
     * note that: Intent.FLAG_ACTIVITY_NEW_TASK
     * @param c
     * @param file
     */
    public static void installApk(Context c, File file) {
        Intent intent = new Intent();
        // in case of upgrade: MIUI 4.1.1(or other 4.x.x like samsung) PackageInstaller 
        // will exists immediately after apk installed without Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        c.startActivity(intent);
    }

    /**
     * overridePendingTransition() should behind the finish() or animation won't work
     * @key 退出动画  退出  动画  
     * @param activity
     */
    public static void finishActivityAnim(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.finish_in_from_left, R.anim.finish_out_to_right);
    }
    
    /**
     * overridePendingTransition() should behind the startActivity() or animation won't work
     * @key 启动动画  启动  动画
     * @param activity
     */
    public static void startActivityAnim(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.start_activity_in_from_right, R.anim.start_activity_out_to_left);
    }
    
    /**
     * 使用JSONObject.optString()如果为null，会返回"null"字符串，因此使用以下方法替代
     * @param jObj
     * @param key
     * @return
     */
    public static String optString(JSONObject jObj, String key) {
        if (jObj.has(key)) {
            try {
                return jObj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
