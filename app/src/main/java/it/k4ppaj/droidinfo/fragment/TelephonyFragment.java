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
import it.k4ppaj.droidinfo.adapter.ClassicAdapter;
import it.k4ppaj.droidinfo.helper.TelephonyHelper;

public class TelephonyFragment extends Fragment {

    private Activity activity;
    private Context context;

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    public TelephonyFragment() {
    }

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
        ListView listView = layoutView.findViewById(R.id.listViewTelephony);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        String[] stringInformation = new String[] {
                getString(R.string.DualSIM),
                getString(R.string.IMEI),
                getString(R.string.Status),
                getString(R.string.PhoneType),
                getString(R.string.Operator),
                getString(R.string.PhoneNumber),
                getString(R.string.NetworkType),
                //getString(R.string.SignalStrength)
        };
        String[] stringValues;
        int[] intIcon = new int[] {
                R.drawable.ic_close_white_24dp,
                R.drawable.ic_close_white_24dp,
                R.drawable.ic_sim_card_white_24dp,
                R.drawable.ic_network_signal_white_24dp,
                R.drawable.ic_sim_card_white_24dp,
                R.drawable.ic_phone_white_24dp,
                R.drawable.ic_network_signal_white_24dp,
        };

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            if (TelephonyHelper.getStatus(activity).equals(activity.getString(R.string.Absent))) {
                stringValues = new String[] {
                        TelephonyHelper.getDualSIM(context),
                        TelephonyHelper.getIMEI(activity),
                        TelephonyHelper.getStatus(activity),
                        getString(R.string.Unknown),
                        getString(R.string.Unknown),
                        getString(R.string.Unknown),
                        getString(R.string.Unknown),
                        //TelephonyHelper.getSisgnalStength(activity)
                };
            } else {
                stringValues = new String[]{
                        TelephonyHelper.getDualSIM(context),
                        TelephonyHelper.getIMEI(activity),
                        TelephonyHelper.getStatus(activity),
                        TelephonyHelper.getPhoneType(activity),
                        TelephonyHelper.getOperator(context),
                        TelephonyHelper.getPhoneNumber(activity),
                        TelephonyHelper.getNetworkType(activity),
                        //TelephonyHelper.getSisgnalStength(activity)
                };
            }
        } else {
            stringValues = new String[] {
                    "No",
                    getString(R.string.Unknown),
                    getString(R.string.NotReady),
                    "GSM",
                    getString(R.string.Unknown),
                    getString(R.string.Unknown),
                    getString(R.string.Unknown)
            };
        }

        ClassicAdapter classicAdapter = new ClassicAdapter(activity, stringInformation, stringValues, intIcon);
        listView.setAdapter(classicAdapter);

        return layoutView;
    }

}
