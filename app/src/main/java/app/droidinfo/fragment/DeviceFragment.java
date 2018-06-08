package app.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.droidinfo.R;
import app.droidinfo.adapter.RecyclerViewAdapter;
import app.droidinfo.helper.DeviceHelper;
import app.droidinfo.helper.RecyclerViewDataHelper;

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

        RecyclerView recyclerView = layoutView.findViewById(R.id.recyclerViewDevice);

        String[] stringInformation = new String[] {
                getString(R.string.Model),
                getString(R.string.Manufacturer),
                getString(R.string.Codename),
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
                    DeviceHelper.getCodename(),
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

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(RecyclerViewDataHelper.recyclerViewFragment(stringInformation, stringValues), context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context.getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
        return layoutView;
    }
}
