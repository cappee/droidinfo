package it.k4ppaj.droidinfo.helper;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;

public class AndroidHelper {

    public static String getAndroidVersion(Activity context) {
        String versionName = "";
        switch (Build.VERSION.SDK_INT) {
            case 21:
                versionName = "Lollipop";
                break;
            case 22:
                versionName = "Lollipop";
                break;
            case 23:
                versionName = "Marshmallow";
                break;
            case 24:
                versionName = "Nougat";
                break;
            case 25:
                versionName = "Nougat";
                break;
            case 26:
                versionName = "Oreo";
                break;
        }
        return versionName + " (" + Build.VERSION.RELEASE + ")";
    }

    public static String getAPILevel() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    // This requires API 23
    public static String getSecurityPatch() {
        return Build.VERSION.SECURITY_PATCH;
    }

    public static String getKernelVersion() {
        return "Linux " + System.getProperty("os.version");
    }

    public static String getKernelArch() {
        return System.getProperty("os.arch");
    }

}
