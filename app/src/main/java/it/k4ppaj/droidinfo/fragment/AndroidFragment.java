package it.k4ppaj.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.adapter.ClassicAdapter;
import it.k4ppaj.droidinfo.helper.AndroidHelper;
import it.k4ppaj.droidinfo.helper.DeviceHelper;

public class AndroidFragment extends Fragment {

    private Activity context;

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    public AndroidFragment() {
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
        View layoutView = inflater.inflate(R.layout.fragment_android, container, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        ListView listView = layoutView.findViewById(R.id.listViewAndroid);

        String[] stringInformation;
        String[] stringValues;
        int[] intIcon;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            if (Build.VERSION.SDK_INT >= 23) {
                stringInformation = new String[] {
                        getString(R.string.AndroidVersion),
                        getString(R.string.APILevel),
                        getString(R.string.SecurityPatch),
                        getString(R.string.KernelVersion),
                        getString(R.string.KernelArch)
                };

                stringValues = new String[] {
                        AndroidHelper.getAndroidVersion(),
                        AndroidHelper.getAPILevel(),
                        AndroidHelper.getSecurityPatch(),
                        AndroidHelper.getKernelVersion(),
                        AndroidHelper.getKernelArch()
                };

                intIcon = new int[] {
                        R.drawable.ic_android_white_24dp,
                        R.drawable.ic_adb_white_24dp,
                        R.drawable.ic_security_white_24dp,
                        R.drawable.ic_developer_board_white_24dp,
                        R.drawable.ic_developer_board_white_24dp,
                };
            } else {
                stringInformation = new String[] {
                        getString(R.string.AndroidVersion),
                        getString(R.string.APILevel),
                        getString(R.string.KernelVersion),
                        getString(R.string.KernelArch)
                };

                stringValues = new String[] {
                        AndroidHelper.getAndroidVersion(),
                        AndroidHelper.getAPILevel(),
                        AndroidHelper.getKernelVersion(),
                        AndroidHelper.getKernelArch()
                };

                intIcon = new int[] {
                        R.drawable.ic_android_white_24dp,
                        R.drawable.ic_adb_white_24dp,
                        R.drawable.ic_developer_board_white_24dp,
                        R.drawable.ic_developer_board_white_24dp,
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

            intIcon = new int[] {
                    R.drawable.ic_android_white_24dp,
                    R.drawable.ic_adb_white_24dp,
                    R.drawable.ic_developer_board_white_24dp,
                    R.drawable.ic_developer_board_white_24dp,
            };
        }



        ClassicAdapter classicAdapter = new ClassicAdapter(context, stringInformation, stringValues, intIcon);
        listView.setAdapter(classicAdapter);
        return layoutView;
    }
}
