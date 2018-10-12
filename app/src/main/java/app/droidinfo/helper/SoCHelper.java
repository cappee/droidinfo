package app.droidinfo.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.microedition.khronos.opengles.GL10;

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

    public static String getIfIs64bit() {
        boolean isArm64Method1 = false;
        boolean isArm64Method2;

        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            if (localBufferedReader.readLine().contains("aarch64")) {
                isArm64Method1 = true;
            }
            localBufferedReader.close();
        } catch (IOException ignored) {
        }

        isArm64Method2 = Build.SUPPORTED_64_BIT_ABIS.length > 0;

        if (isArm64Method1 || isArm64Method2) {
            return "64bit";
        } else {
            return "32bit";
        }
    }

    public static String getCPUModel() {
        String cpu = getCPUInfoMap().get("Hardware");
        if (cpu == null) {
            cpu = getCPUInfoMap().get("model name");
        }
        return cpu;
    }

    public static String getCPUCores() {
        return String.valueOf(Runtime.getRuntime().availableProcessors()) + " cores";
    }

    public static String getCPUFreq() {
        return String.valueOf(getMinCPUFreq(0)) + " - " + String.valueOf(getMaxCPUFreq(0)) + " MHz";
    }

    public static String getCurrentCPUFreq(int core) {
        int currentFreq = -1;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/sys/devices/system/cpu/cpu" + core + "/cpufreq/scaling_cur_freq", "r");
            boolean done = false;
            while (!done) {
                String line = randomAccessFile.readLine();
                if (null == line) {
                    done = true;
                    break;
                }
                int timeInState = Integer.parseInt(line);
                if (timeInState > 0) {
                    int freq = timeInState / 1000;
                    if (freq > currentFreq) {
                        currentFreq = freq;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(currentFreq);
    }

    private static int getMinCPUFreq(int core) {
        int minFreq = -1;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/sys/devices/system/cpu/cpu" + core + "/cpufreq/cpuinfo_min_freq", "r");
            boolean done = false;
            while (!done) {
                String line = randomAccessFile.readLine();
                if (null == line) {
                    done = true;
                    break;
                }
                int timeInState = Integer.parseInt(line);
                if (timeInState > 0) {
                    int freq = timeInState / 1000;
                    if (freq > minFreq) {
                        minFreq = freq;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return minFreq;
    }

    private static int getMaxCPUFreq(int core) {
        int maxFreq = -1;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/sys/devices/system/cpu/cpu" + core + "/cpufreq/cpuinfo_max_freq", "r");
            boolean done = false;
            while (!done) {
                String line = randomAccessFile.readLine();
                if (null == line) {
                    done = true;
                    break;
                }
                int timeInState = Integer.parseInt(line);
                if (timeInState > 0) {
                    int freq = timeInState / 1000;
                    if (freq > maxFreq) {
                        maxFreq = freq;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxFreq;
    }

    public static String getCPUGovernor(int core) {
        String governor = "";
        String file = "/sys/devices/system/cpu/cpu" + core + "/cpufreq/scaling_governor";

        if (new File(file).exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
                governor = bufferedReader.readLine();

                if (bufferedReader != null)
                    bufferedReader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return governor;
    }

    public static String getBogoMIPS() {
        String bogomips = String.valueOf(getCPUInfoMap().get("bogomips"));
        if (bogomips.equals("null")) {
            bogomips = String.valueOf(getCPUInfoMap().get("BogoMIPS"));
        }
        return bogomips;
    }

    public static String getGPUVendor(GL10 gl10) {
        return gl10.glGetString(GL10.GL_VENDOR);
    }

    public static String getGPURenderer(GL10 gl10) {
        return gl10.glGetString(GL10.GL_RENDERER);
    }

    public static String getOpenGLVersion(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.getGlEsVersion();
    }



}
