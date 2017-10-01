package it.k4ppaj.droidinfo.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES30;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SoCHelper {

    public static Map<String, String> getCPUInfoMap() {
        Map<String, String> map = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            while (scanner.hasNextLine()) {
                String[] vals = scanner.nextLine().split(": ");
                if (vals.length > 1) map.put(vals[0].trim(), vals[1].trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getCPUModel() {
        return getCPUInfoMap().get("model name");
    }

    public static String getCPUCores() {
        return String.valueOf(Runtime.getRuntime().availableProcessors()) + " cores";
    }

    public static String getCPUFreq() {
        int maxFreq = -1;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r");
            boolean done = false;
            while (!done) {
                String line = randomAccessFile.readLine();
                if (null == line) {
                    done = true;
                    break;
                }
                String[] splits = line.split("\\s+");
                assert (splits.length == 2);
                int timeInState = Integer.parseInt(splits[1]);
                if (timeInState > 0) {
                    int freq = Integer.parseInt(splits[0]) / 1000;
                    if (freq > maxFreq) {
                        maxFreq = freq;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(maxFreq) + " MHz";
    }

    public static String getGPUVendor() {
        return GLES30.glGetString(GLES30.GL_VENDOR);
    }

    public static String getGPURenderer() {
        return GLES30.glGetString(GLES30.GL_RENDERER);
    }

    public static String getOpenGLVersion(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.getGlEsVersion();
    }



}
