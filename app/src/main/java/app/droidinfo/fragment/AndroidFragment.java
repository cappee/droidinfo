package app.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import app.droidinfo.helper.AndroidHelper;
import app.droidinfo.helper.RecyclerViewDataHelper;

public class AndroidFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_android, container, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        RecyclerView recyclerView = layoutView.findViewById(R.id.recyclerViewAndroid);

        String[] stringInformation;
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            if (Build.VERSION.SDK_INT >= 23) {
                stringInformation = new String[] {
                        getString(R.string.AndroidVersion),
                        getString(R.string.APILevel),
                        getString(R.string.SecurityPatch),
                        getString(R.string.BuildID),
                        getString(R.string.KernelVersion),
                        getString(R.string.KernelArch)
                };

                stringValues = new String[] {
                        AndroidHelper.getAndroidVersion(),
                        AndroidHelper.getAPILevel(),
                        AndroidHelper.getSecurityPatch(),
                        AndroidHelper.getBuildID(),
                        AndroidHelper.getKernelVersion(),
                        AndroidHelper.getKernelArch()
                };
            } else {
                stringInformation = new String[] {
                        getString(R.string.AndroidVersion),
                        getString(R.string.APILevel),
                        getString(R.string.BuildID),
                        getString(R.string.KernelVersion),
                        getString(R.string.KernelArch)
                };

                stringValues = new String[] {
                        AndroidHelper.getAndroidVersion(),
                        AndroidHelper.getAPILevel(),
                        AndroidHelper.getBuildID(),
                        AndroidHelper.getKernelVersion(),
                        AndroidHelper.getKernelArch()
                };
            }
        } else {
            stringInformation = new String[] {
                    getString(R.string.AndroidVersion),
                    getString(R.string.APILevel),
                    getString(R.string.KernelVersion),
                    getString(R.string.KernelArch)
            };

            stringValues = new String[] {
                    "Oreo (8.0.1)",
                    "26",
                    "Linux 3.18.48+",
                    "Arm64"
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
