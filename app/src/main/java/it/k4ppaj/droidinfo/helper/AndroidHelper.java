package it.k4ppaj.droidinfo.helper;

import android.os.Build;
import android.support.annotation.RequiresApi;

public class AndroidHelper {

    public static String getAndroidVersion() {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getSecurityPatch() {
        return Build.VERSION.SECURITY_PATCH;
    }

    public static String getKernelVersion() {
        return "Linux " + System.getProperty("os.version");
    }

    public static String getKernelArch() {
        String arch = System.getProperty("os.arch");
        switch (arch) {
            case "armv7l":
                return "ARMv7l";
            case "aarch64": // 64 bit (Arch?)
                return "Arch64";
            case "32":
                return "Unknown arch wtf u use xD";
            default:
                return arch;
        }
    }

}
