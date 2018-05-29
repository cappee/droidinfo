package app.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import app.droidinfo.R;
import app.droidinfo.adapter.SimpleAdapter;

public class SensorFragment extends Fragment implements SensorEventListener{

    private Activity activity;

    private SensorManager mSensorManager;

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //mSensorManager.registerListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_sensor, container, false);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        mSensorManager =(SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

        ListView listView = layoutView.findViewById(R.id.listViewSensor);

        String[] stringInformation = new String[] {

        };
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {

            };
        } else {
            stringValues = new String[] {
                    "1440x2880",
                    "538 dpi",
                    "6.00\"",
                    "60Hz"
            };
        }

        SimpleAdapter adapter = new SimpleAdapter(activity, stringInformation, stringValues);
        //listView.setAdapter(adapter);
        return layoutView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
