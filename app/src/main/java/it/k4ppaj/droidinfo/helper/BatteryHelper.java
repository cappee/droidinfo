package it.k4ppaj.droidinfo.helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import it.k4ppaj.droidinfo.R;

public class BatteryHelper {

    private static BatteryHelper instance;

    public Activity context;
    public BatteryManager batteryManager;

    private BatteryHelper(Activity context) {
        this.context = context;
    }

    public static BatteryHelper newInstance(Activity context) {
        if (instance == null) {
            instance = new BatteryHelper(context);
        }
        return instance;
    }

    public String getHealth() {
        final String[] health = new String[1];
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                health[0] = getHealthFromInteger(intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0));
            }
        };
        IntentFilter intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(broadcastReceiver, intentfilter);
        if (health[0] != null) {
            context.unregisterReceiver(broadcastReceiver);
            return health[0];
        } else {
            return context.getString(R.string.Unknown);
        }
    }

    private String getHealthFromInteger(int value) {
        switch (value) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                return context.getString(R.string.Cold);
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return context.getString(R.string.Dead);
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return context.getString(R.string.Good);
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return context.getString(R.string.OverVoltage);
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return context.getString(R.string.OverHeat);
            default:
                return context.getString(R.string.Unknown);
        }
    }

    public String getPercentage() {
        if (batteryManager == null) {
            batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        }
        return String.valueOf(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
    }

    /*public String getStatus() {

    }*/

    public String getTemperature() {
        final int[] temperature = new int[1];
        temperature[0] = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                temperature[0] = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            }
        };
        IntentFilter intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(broadcastReceiver, intentfilter);
        if (temperature[0] != 0) {
            context.unregisterReceiver(broadcastReceiver);
            return String.valueOf(temperature[0]);
        } else {
            return context.getString(R.string.Unknown);
        }
    }
}
