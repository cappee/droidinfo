package it.k4ppaj.droidinfo.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.adapter.ClassicAdapter;
import it.k4ppaj.droidinfo.helper.DeviceHelper;

public class DeviceFragment extends Fragment {

    private Activity context;

    public DeviceFragment() {
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
        View layoutView = inflater.inflate(R.layout.fragment_device, container, false);

        ListView listView = layoutView.findViewById(R.id.listViewDevice);

        String[] stringInformation = new String[] {
                getString(R.string.Model),
                getString(R.string.Manufacturer),
                getString(R.string.RAM),
                getString(R.string.InternalStorage),
                getString(R.string.ExternalStorage),
                getString(R.string.RootAccess)
        };

        String[] stringValues = new String[] {
                DeviceHelper.getModel(),
                DeviceHelper.getManufacturer(),
                DeviceHelper.getRAM(),
                DeviceHelper.getInternalStorage(),
                DeviceHelper.getExternalStorage(context),
                DeviceHelper.getRootAccess()
        };

        ClassicAdapter classicAdapter = new ClassicAdapter(context, stringInformation, stringValues);
        listView.setAdapter(classicAdapter);
        return layoutView;
    }
}
