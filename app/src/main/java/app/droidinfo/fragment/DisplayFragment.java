package app.droidinfo.fragment;

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

import app.droidinfo.R;
import app.droidinfo.adapter.SimpleAdapter;
import app.droidinfo.helper.DisplayHelper;

public class DisplayFragment extends Fragment {

    private Activity activity;

    private String SCREEN_INCHES = "SCREEN_INCHES";
    private String RESOLUTION = "RESOLUTION";

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_display, container, false);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        ListView listView = layoutView.findViewById(R.id.listViewDisplay);

        String[] stringInformation = new String[] {
                getString(R.string.Resolution),
                getString(R.string.DPI),
                getString(R.string.ScreenSize),
                getString(R.string.RefreshValue)
        };
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {
                    sharedPreferences.getString(RESOLUTION, getString(R.string.Unknown)),
                    DisplayHelper.getDPI(activity),
                    sharedPreferences.getString(SCREEN_INCHES, getString(R.string.Unknown)),
                    DisplayHelper.getRefreshValue(activity)
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
        listView.setAdapter(adapter);
        return layoutView;
    }
}
