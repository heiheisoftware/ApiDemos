
package com.heihei.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MobileDataUtils {

    public static boolean isGprsOpen(Context ctx)
    {
        int type = getConnectivityStatus(ctx);
        return type == 0;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return 1;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return 0;
        }
        return -1;
    }

    private static boolean isMobileDataEnable(ConnectivityManager cm)
    {
        Class cmClass = cm.getClass();
        Class[] argClasses = null;
        Object[] argObject = null;

        Boolean isOpen = Boolean.valueOf(false);
        try {
            Method method = cmClass.getMethod("getMobileDataEnabled", argClasses);
            isOpen = (Boolean) method.invoke(cm, argObject);
        } catch (Exception localException) {
        }
        return isOpen.booleanValue();
    }

    public static boolean setMobileDataEnabled(Context ctx, boolean enable) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if ((!enable) && (!isMobileDataEnable(cm))) {
            return true;
        }
        return setMobileDataEnabled(cm, enable);
    }

    public static boolean isMobileDataEnable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return isMobileDataEnable(cm);
    }

    private static boolean setMobileDataEnabled(ConnectivityManager cm, boolean enable)
    {
        Class cmClass = cm.getClass();
        Class[] argClasses = new Class[1];
        argClasses[0] = Boolean.TYPE;
        try
        {
            Method method = cmClass.getMethod("setMobileDataEnabled", argClasses);
            method.invoke(cm, new Object[] {
                    Boolean.valueOf(enable)
            });
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
