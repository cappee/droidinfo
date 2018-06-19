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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import app.droidinfo.R;
import app.droidinfo.adapter.RecyclerViewAdapter;
import app.droidinfo.helper.RecyclerViewDataHelper;

public class SensorFragment extends Fragment implements SensorEventListener{

    private Activity activity;
    private Context context;

    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer, mSensorMagnetometer, mSensorGyroscope, mSensorProximity, mSensorAmbientTemperature, mSensorLight, mSensorPressure, mSensorRelativeHumidity, mSensorDeviceTemperature;

    private float mAccelerometerX;
    private float mAccelerometerY;
    private float mAccelerometerZ;
    private String mProximity;
    private float mGyroscopeX;
    private float mGyroscopeY;
    private float mGyroscopeZ;
    private float mMagnetometerX;
    private float mMagnetometerY;
    private float mMagnetometerZ;
    private float mAmbientTemperature;
    private float mLight;
    private float mPressure;
    private float mHumidity;
    private float mDeviceTemperature;

    private SharedPreferences sharedPreferences;
    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    private RecyclerView recyclerView;

    private String[] stringInformation;
    private String[] stringValues;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //SENSOR_NORMAL_DELAY=3 but it's too short, so 10 is a good delay ~gabrielecappellaro
        // LEL - STOCOPYANYTHING
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorRelativeHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorDeviceTemperature, SensorManager.SENSOR_DELAY_NORMAL);
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

        sharedPreferences = activity.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            mSensorRelativeHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            mSensorDeviceTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
            mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorRelativeHumidity, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mSensorDeviceTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        }

        recyclerView = layoutView.findViewById(R.id.recyclerViewSensor);

        stringInformation = new String[] {
                getString(R.string.Accelerometer),
                getString(R.string.Magnetometer),
                getString(R.string.Gyroscope),
                getString(R.string.Proximity),
                getString(R.string.AmbientTemperature),
                getString(R.string.Light),
                getString(R.string.Pressure),
                getString(R.string.Humidity)
        };

        stringValues = new String[] {};

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context.getApplicationContext(), LinearLayoutManager.VERTICAL));

        return layoutView;
    }

    private void getSensorData() {
        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {
                    "x: " + String.format(Locale.ENGLISH, "%.2f", mAccelerometerX) + " m/s2 / y: " + String.format(Locale.ENGLISH, "%.2f", mAccelerometerY) + " m/s2 / z: " + String.format(Locale.ENGLISH, "%.2f", mAccelerometerZ) + " m/s2",
                    "x: " + String.format(Locale.ENGLISH, "%.2f", mMagnetometerX) + " μT / y: " + String.format(Locale.ENGLISH, "%.2f", mMagnetometerY) + " μT / z: " + String.format(Locale.ENGLISH, "%.2f", mMagnetometerZ) + " μT",
                    "x: " + String.format(Locale.ENGLISH, "%.2f", mGyroscopeX) + " rad/s / y: " + String.format(Locale.ENGLISH, "%.2f", mGyroscopeY) + " rad/s / z: " + String.format(Locale.ENGLISH, "%.2f", mGyroscopeZ) + " rad/s",
                    mProximity,
                    String.valueOf(mAmbientTemperature) + " C°",
                    String.valueOf(mLight) + " lx",
                    String.valueOf(mPressure) + " hPa",
                    String.valueOf(mHumidity) + " %",
            };
        } else {
            stringValues = new String[] {
                    // TODO: Insert value for default device
            };
        }

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(RecyclerViewDataHelper.recyclerViewFragment(stringInformation, stringValues), context);
        //recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerometerX = sensorEvent.values[0];
                mAccelerometerY = sensorEvent.values[1];
                mAccelerometerZ = sensorEvent.values[2];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetometerX = sensorEvent.values[0];
                mMagnetometerY = sensorEvent.values[1];
                mMagnetometerZ = sensorEvent.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                mGyroscopeX = sensorEvent.values[0];
                mGyroscopeY = sensorEvent.values[1];
                mGyroscopeZ = sensorEvent.values[2];
                break;
            case Sensor.TYPE_LIGHT:
                mLight = sensorEvent.values[0];
                break;
            case Sensor.TYPE_PRESSURE:
                mPressure = sensorEvent.values[0];
                break;
            case Sensor.TYPE_PROXIMITY:
                if (sensorEvent.values[0] < mSensorProximity.getMaximumRange()) {
                    mProximity = getString(R.string.Near) + " (" + sensorEvent.values[0] + ")";
                } else {
                    mProximity = getString(R.string.Far) + " (" + sensorEvent.values[0] + ")";
                }
                break;
            case Sensor.TYPE_GRAVITY:
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mHumidity = sensorEvent.values[0];
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mAmbientTemperature = sensorEvent.values[0];
                break;
        }

        getSensorData();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        getSensorData();
    }
}
