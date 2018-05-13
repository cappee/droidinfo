package it.k4ppaj.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.adapter.SimpleAdapter;

public class BatteryFragment extends Fragment {

    private Activity context;

    private String BATTERY_HEALTH = "BATTERY_HEALTH";
    private String BATTERY_PERCENTAGE = "BATTERY_PERCENTAGE";
    private String BATTERY_POWER_SOURCE = "BATTERY_PLUGGED_SOURCE";
    private String BATTERY_STATUS = "BATTERY_STATUS";
    private String BATTERY_TEMPERATURE = "BATTERY_TEMPERATURE";
    private String BATTERY_TECHNOLOGY = "BATTERY_TECHNOLOGY";
    private String BATTERY_VOLTAGE = "BATTERY_VOLTAGE";
    private String BATTERY_CAPACITY = "BATTERY_CAPACITY";

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_battery, container, false);
        ListView listViewBattery = layoutView.findViewById(R.id.listViewBattery);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        String[] stringInformation = new String[] {
                getString(R.string.Health),
                getString(R.string.Percentage),
                getString(R.string.PowerSource),
                getString(R.string.Status),
                getString(R.string.Temperature),
                getString(R.string.Technology),
                getString(R.string.Voltage),
                getString(R.string.Capacity)
        };
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {
                    sharedPreferences.getString(BATTERY_HEALTH, ""),
                    sharedPreferences.getString(BATTERY_PERCENTAGE, ""),
                    sharedPreferences.getString(BATTERY_POWER_SOURCE, ""),
                    sharedPreferences.getString(BATTERY_STATUS, ""),
                    sharedPreferences.getString(BATTERY_TEMPERATURE, ""),
                    sharedPreferences.getString(BATTERY_TECHNOLOGY, ""),
                    sharedPreferences.getString(BATTERY_VOLTAGE, ""),
                    sharedPreferences.getString(BATTERY_CAPACITY, "")
            };
        } else {
            stringValues = new String[] {
                    getString(R.string.Good),
                    "100%",
                    getString(R.string.AC),
                    getString(R.string.Full),
                    "28.0°C",
                    "Li-ion",
                    "3.800 mV",
                    "3.520 mAh"
            };
        }

        SimpleAdapter adapter = new SimpleAdapter(context, stringInformation, stringValues);
        listViewBattery.setAdapter(adapter);
        return layoutView;
    }

}
