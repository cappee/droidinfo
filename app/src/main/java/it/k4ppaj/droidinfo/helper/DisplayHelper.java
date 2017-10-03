package it.k4ppaj.droidinfo.helper;

import android.app.Activity;
import android.content.Context;
import java.text.DecimalFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Locale;

public class DisplayHelper {

    public static String getResolution(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        String stringResolution = width + "x" + height;
        return stringResolution;
    }

    public static String getPixelAmount(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return String.valueOf(metrics.heightPixels * metrics.widthPixels) + "px";
    }

    public static String getDPI(Activity context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int densityDPI = (int)(metrics.density * 160f);
        return String.valueOf(densityDPI);
    }

    public static String getScreenSize(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        String screenSize = new DecimalFormat("##.##").format(Math.sqrt(Math.pow(dm.widthPixels / dm.xdpi, 2) + Math.pow(dm.heightPixels / dm.ydpi, 2)));
        return String.valueOf(screenSize) + "\"";
    }

    public static String getRefreshValue(Activity context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshValue = display.getRefreshRate();
        return String.format(Locale.ENGLISH, "%.2f", refreshValue) + "Hz";
    }

}
