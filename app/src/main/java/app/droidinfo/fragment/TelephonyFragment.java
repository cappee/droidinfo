package app.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.droidinfo.R;
import app.droidinfo.adapter.RecyclerViewAdapter;
import app.droidinfo.helper.RecyclerViewDataHelper;
import app.droidinfo.helper.TelephonyHelper;

public class TelephonyFragment extends Fragment {

    private Activity activity;
    private Context context;

    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_telephony, container, false);
        recyclerView = layoutView.findViewById(R.id.recyclerViewTelephony);

        sharedPreferences = context.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        String[] stringInformation = new String[] {
                getString(R.string.IMEI),
                getString(R.string.Status),
                "NFC",
                getString(R.string.PhoneType),
                getString(R.string.Operator),
                getString(R.string.PhoneNumber),
                getString(R.string.NetworkType),
                getString(R.string.SignalStrength)
        };
        String[] stringValues;

        if (Build.VERSION.SDK_INT >= 23) {
            if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
                if (TelephonyHelper.getStatus(activity).equals(activity.getString(R.string.Absent))) {
                    stringValues = new String[] {
                            TelephonyHelper.getIMEI(activity),
                            TelephonyHelper.getStatus(activity),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            TelephonyHelper.getSignalStength(activity)
                    };
                } else {
                    stringValues = new String[]{
                            TelephonyHelper.getIMEI(activity),
                            TelephonyHelper.getStatus(activity),
                            TelephonyHelper.getPhoneType(activity),
                            TelephonyHelper.getOperator(context),
                            TelephonyHelper.getPhoneNumber(activity),
                            TelephonyHelper.getNetworkType(activity),
                            TelephonyHelper.getSignalStength(activity)
                    };
                }
            } else {
                stringValues = new String[] {
                        "No",
                        getString(R.string.Unknown),
                        getString(R.string.NotReady),
                        "Enabled",
                        "GSM",
                        getString(R.string.Unknown),
                        getString(R.string.Unknown),
                        getString(R.string.Unknown),
                        getString(R.string.Unknown)
                };
            }
        } else {
            if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
                if (TelephonyHelper.getStatus(activity).equals(activity.getString(R.string.Absent))) {
                    stringValues = new String[] {
                            TelephonyHelper.getIMEI(activity),
                            TelephonyHelper.getStatus(activity),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            TelephonyHelper.getSignalStength(activity)
                    };
                } else {
                    stringValues = new String[]{
                            TelephonyHelper.getIMEI(activity),
                            TelephonyHelper.getStatus(activity),
                            TelephonyHelper.getPhoneType(activity),
                            getString(R.string.Unknown),
                            getString(R.string.Unknown),
                            TelephonyHelper.getNetworkType(activity),
                            TelephonyHelper.getSignalStength(activity)
                    };
                }
            } else {
                stringValues = new String[] {
                        "No",
                        getString(R.string.Unknown),
                        getString(R.string.NotReady),
                        "Enabled",
                        "GSM",
                        getString(R.string.Unknown),
                        getString(R.string.Unknown),
                        getString(R.string.Unknown)
                };
            }
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
