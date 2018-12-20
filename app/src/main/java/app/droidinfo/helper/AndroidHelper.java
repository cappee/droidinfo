package app.droidinfo.helper;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.RequiresApi;
import app.droidinfo.R;

import static android.content.ContentValues.TAG;

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
            case 27:
                versionName = "Oreo";
                break;
            case 28:
                versionName = "Pie";
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

    public static String getBuildID() {
        return Build.DISPLAY;
    }

    public static String getTreble(Context context) {
        String output = getSystemProperty("ro.treble.enabled");
        if (output.equals("true")) {
            return context.getString(R.string.Supported);
        } else {
            return context.getString(R.string.Unsupported);
        }
    }

    public static String getCustomRomName(Context context) {
        String output = getSystemProperty("org.pixelexperience.version");
        if (output.toLowerCase().contains("PixelExperience".toLowerCase())) {
            return "PixelExperience";
        } else {
            return context.getString(R.string.StockFirmware);
        }
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

    public static String getSELinuxStatus() {
        if (isSELinuxEnforcing()) {
            return "Enforcing";
        } else {
            return "Permissive";
        }
    }

    private static boolean isSELinuxEnforcing() {
        StringBuffer output = new StringBuffer();
        java.lang.Process process;
        try {
            process = Runtime.getRuntime().exec("getenforce");
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line);
            }
        } catch (Exception e) {
            Log.e(TAG, "OS does not support getenforce");
            // If getenforce is not available to the device, assume the device is not enforcing
            e.printStackTrace();
            return false;
        }
        String response = output.toString();
        if ("Enforcing".equals(response)) {
            return true;
        } else if ("Permissive".equals(response)) {
            return false;
        } else {
            Log.e(TAG, "getenforce returned unexpected value, unable to determine selinux!");
            // If getenforce is modified on this device, assume the device is not enforcing
            return false;
        }
    }

    public static String getSystemProperty(String key) {
        String value = null;

        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

}
