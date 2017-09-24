package it.k4ppaj.droidinfo.helper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceHelper {

    public static String getAndroidVersion() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String versionName = fields[Build.VERSION.SDK_INT + 1].getName();
        return Build.VERSION.RELEASE + " - " + versionName;
    }

    public static String getAPILevel() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getRAM() {
        RandomAccessFile randomAccessFile = null;
        String load = null;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double totRAM = 0;
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
        return lastValue;
    }

    public static String getInternalStorage() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        String lastValue = "";
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

}
