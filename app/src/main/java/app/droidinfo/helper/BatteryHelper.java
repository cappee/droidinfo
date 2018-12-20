package app.droidinfo.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import app.droidinfo.R;

public class BatteryHelper {

    private static boolean isBatteryPresent(Intent intent) {
        return intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
    }

    public static String getHealth(Intent intent, Activity activity) {
        if (isBatteryPresent(intent)) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    return activity.getString(R.string.Cold);
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    return activity.getString(R.string.Dead);
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    return activity.getString(R.string.Good);
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    return activity.getString(R.string.OverVoltage);
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    return activity.getString(R.string.OverHeat);
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    return activity.getString(R.string.UnspecifiedFailure);
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    return activity.getString(R.string.Unknown);
                default:
                    return activity.getString(R.string.Unknown);
            }
        } else {
            return activity.getString(R.string.NotPresent);
        }
    }

    public static String getPercentage(Intent intent, Activity activity) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if (level != -1 && scale != -1) {
            return String.valueOf((int) ((level / (float) scale) * 100f)) + "%";
        } else {
            return activity.getString(R.string.Unknown);
        }
    }

    public static String getPluggedSource(Intent intent, Activity activity) {
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return activity.getString(R.string.Wireless);
            case BatteryManager.BATTERY_PLUGGED_USB:
                return activity.getString(R.string.USB);
            case BatteryManager.BATTERY_PLUGGED_AC:
                return activity.getString(R.string.AC);
            default:
                return activity.getString(R.string.Disconnected);
        }
    }

    public static String getStatus(Intent intent, Activity activity) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return activity.getString(R.string.Charging);
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return activity.getString(R.string.Discharging);
            case BatteryManager.BATTERY_STATUS_FULL:
                return activity.getString(R.string.Full);
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return activity.getString(R.string.Unknown);
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return activity.getString(R.string.NotCharging);
            default:
                return activity.getString(R.string.Unknown);
        }
    }

    public static String getTechnology(Intent intent, Activity activity) {
        if (intent.getExtras() != null) {
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            if (!"".equals(technology)) {
                return technology;
            } else {
                return activity.getString(R.string.Unknown);
            }
        } else {
            return activity.getString(R.string.Unknown);
        }
    }

    public static String getTemperature(Intent intent, Activity activity) {
        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

        if (temperature > 0) {
            return String.valueOf(((float) temperature) / 10f) + "°C";
        } else {
            return activity.getString(R.string.Unknown);
        }
    }

    public static String getVoltage(Intent intent, Activity activity) {
        int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

        if (voltage > 0) {
            return String.valueOf(voltage) + " mV";
        } else {
            return activity.getString(R.string.Unknown);
        }
    }

    public static String getCapacity(Context context) {
        Activity activity = (Activity) context;
        Object powerProfile_ = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            powerProfile_ = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double batteryCapacity = 0;

        try {
            batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower", java.lang.String.class).invoke(powerProfile_, "battery.capacity");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (batteryCapacity == 1000.0) {
            return activity.getString(R.string.Unknown);
        } else {
            return String.valueOf(batteryCapacity) + " mAh";
        }
    }

}
