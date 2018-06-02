package app.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import app.droidinfo.R;
import app.droidinfo.adapter.SimpleAdapter;
import app.droidinfo.helper.DeviceHelper;

public class DeviceFragment extends Fragment {

    private Activity context;

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
        View layoutView = inflater.inflate(R.layout.fragment_device, container, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        ListView listView = layoutView.findViewById(R.id.listViewDevice);

        String[] stringInformation = new String[] {
                getString(R.string.Model),
                getString(R.string.Manufacturer),
                getString(R.string.RAM),
                getString(R.string.InternalStorage),
                getString(R.string.ExternalStorage),
                getString(R.string.RootAccess),
                getString(R.string.SELinux)
        };
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {
                    DeviceHelper.getModel(),
                    DeviceHelper.getManufacturer(),
                    DeviceHelper.getRAM(context),
                    DeviceHelper.getInternalStorage(),
                    DeviceHelper.getExternalStorage(context),
                    DeviceHelper.getRootAccess(context),
                    DeviceHelper.getSELinuxStatus()
            };
        } else {
            stringValues = new String[] {
                    "Pixel 2 XL",
                    "Google LLC",
                    "4 GB",
                    "64 GB",
                    getString(R.string.NotMounted),
                    getString(R.string.Yes),
                    "Enforcing"
            };
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, stringInformation, stringValues);
        listView.setAdapter(simpleAdapter);
        return layoutView;
    }
}
