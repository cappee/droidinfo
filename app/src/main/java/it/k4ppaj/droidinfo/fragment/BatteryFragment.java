package it.k4ppaj.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.adapter.ClassicAdapter;

public class BatteryFragment extends Fragment {

    private Activity context;

    public BatteryFragment() {
    }

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

        //ListView listViewBattery = (ListView) layoutView.findViewById(R.id.listViewBattery);

        String[] stringInformation = new String[] {
                getString(R.string.Health),
                getString(R.string.Percentage),
                getString(R.string.PowerSource),
                getString(R.string.Status),
                getString(R.string.Temperature),
                getString(R.string.Voltage)
        };

        /*String[] stringValues = new String[] {

        };*/

        //ClassicAdapter adapter = new ClassicAdapter(context, stringInformation, stringValues);
        //listViewBattery.setAdapter(adapter);
        return layoutView;
    }

}
