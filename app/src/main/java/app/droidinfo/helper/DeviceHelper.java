package app.droidinfo.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import androidx.core.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.droidinfo.R;

import static android.content.ContentValues.TAG;

public class DeviceHelper {

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getCodename() {
        return Build.DEVICE;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getRAM(Activity context) {
        RandomAccessFile randomAccessFile;
        String load;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double totRAM;
        String lastValue = "";

        try {
            randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
            load = randomAccessFile.readLine();

            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(load);
            String value = "";

            while (matcher.find()) {
                value = matcher.group(1);
            }

            randomAccessFile.close();

            totRAM = Double.parseDouble(value);

            double mb = totRAM / 1024.0;
            double gb = totRAM / 1048576.0;
            double tb = totRAM / 1073741824.0;

            if (tb > 1) {
                lastValue = decimalFormat.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = decimalFormat.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = decimalFormat.format(mb).concat(" MB");
            } else {
                lastValue = decimalFormat.format(totRAM).concat(" KB");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        long ramAvailable = mi.availMem / 0x100000L; // 0x100000L is a mebibyte - StopCopyAnything

        String ramAvailableString = "";
        if (ramAvailable < 1024) {
            ramAvailableString = ramAvailable + " MB";
        } else if (ramAvailable > 1024) {
            ramAvailableString = (ramAvailable / 1024) + " GB";
        }

        return ramAvailableString + " / " + lastValue;
    }

    private static String getExternalStorageDirectories(Activity context) {

        List<String> results = new ArrayList<>();

        // Method 1 for KitKat & above
        File[] externalDirs = context.getExternalFilesDirs(null);

        for (File file : externalDirs) {
            String path = file.getPath().split("/Android")[0];

            boolean addPath;

            addPath = Environment.isExternalStorageRemovable(file);

            if(addPath){
                results.add(path);
            }
        }

        if(results.isEmpty()) { // Method 2 for all versions
            // better variation of: http://stackoverflow.com/a/40123073/5002496
            String output = "";
            try {
                final Process process = new ProcessBuilder().command("mount | grep /dev/block/vold")
                        .redirectErrorStream(true).start();
                process.waitFor();
                final InputStream is = process.getInputStream();
                final byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    output = output + new String(buffer);
                }
                is.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            if(!output.trim().isEmpty()) {
                String devicePoints[] = output.split("\n");
                for(String voldPoint: devicePoints) {
                    results.add(voldPoint.split(" ")[2]);
                }
            }
        }

        // Below few lines is to remove paths which may not be external memory card, like OTG (feel free to comment them out)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().matches(".*[0-9a-f]{4}[-][0-9a-f]{4}")) {
                    // Log.d(LOG_TAG, results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        } else {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().contains("ext") && !results.get(i).toLowerCase().contains("sdcard")) {
                    // Log.d(LOG_TAG, results.get(i)+" might not be extSDcard");
                    results.remove(i--);
                }
            }
        }

        String storageDirectories = "";
        for(int i = 0; i < results.size(); ++i) storageDirectories = storageDirectories + results.get(i);

        return storageDirectories;
    }

    public static String getInternalStorage() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        String lastValue;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;

        double kb = totalSize / 1024.0;
        double mb = totalSize / 1048576.0;
        double gb = totalSize / 1073741824.0;

        if (gb > 1) {
            lastValue = decimalFormat.format(gb).concat(" GB");
        } else if (mb > 1) {
            lastValue = decimalFormat.format(mb).concat(" MB");
        } else if (kb > 1) {
            lastValue = decimalFormat.format(kb).concat(" KB");
        } else {
            lastValue = decimalFormat.format(totalSize).concat(" bytes");
        }
        return String.valueOf(lastValue);
    }

    public static String getExternalStorage(Activity context) {
        if (ContextCompat.getExternalFilesDirs(context, null).length >= 2) {
            String lastValue;

            StatFs statFs = new StatFs(getExternalStorageDirectories(context));
            long blockSize = statFs.getBlockSizeLong();
            long totalBlocks = statFs.getBlockCountLong();

            long totalSize = blockSize * totalBlocks;

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            double kb = totalSize / 1024.0;
            double mb = totalSize / 1048576.0;
            double gb = totalSize / 1073741824.0;

            if (gb > 1) {
                lastValue = decimalFormat.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = decimalFormat.format(mb).concat(" MB");
            } else if (kb > 1) {
                lastValue = decimalFormat.format(kb).concat(" KB");
            } else {
                lastValue = decimalFormat.format(totalSize).concat(" bytes");
            }
            return String.valueOf(lastValue);
        } else {
            return context.getString(R.string.NotMounted);
        }
    }

    public static String getSELinuxStatus() {
        if (isSELinuxEnforcing()) {
            return "Enforcing";
        } else {
            return "Permissive";
        }
    }

    public static boolean isSELinuxEnforcing() {
        StringBuffer output = new StringBuffer();
        Process process;
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

    public static String getRootAccess(Activity context) {
        boolean isRooted = checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
        if (isRooted) {
            return context.getString(R.string.Yes);
        } else {
            return context.getString(R.string.No);
        }
    }

    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su" };
        for (String path : paths) { // for each bitch hahahha joke man xD - StopCopyAnything
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] {"/system/xbin/which", "su"});
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return bufferedReader.readLine() != null;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

}
