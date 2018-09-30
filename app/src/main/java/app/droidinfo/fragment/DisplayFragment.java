package app.droidinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.droidinfo.R;
import app.droidinfo.adapter.RecyclerViewAdapter;
import app.droidinfo.helper.DisplayHelper;
import app.droidinfo.helper.RecyclerViewDataHelper;

public class DisplayFragment extends Fragment {

    private Activity activity;
    private Context context;

    private String SCREEN_INCHES = "SCREEN_INCHES";
    private String RESOLUTION = "RESOLUTION";

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_display, container, false);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("DroidInfo", Context.MODE_PRIVATE);

        RecyclerView recyclerView = layoutView.findViewById(R.id.recyclerViewDisplay);

        String[] stringInformation = new String[] {
                getString(R.string.Resolution),
                getString(R.string.DPI),
                getString(R.string.ScreenSize),
                getString(R.string.RefreshValue),
                //getString(R.string.Brightness)
        };
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {
                    sharedPreferences.getString(RESOLUTION, getString(R.string.Unknown)),
                    DisplayHelper.getDPI(activity),
                    sharedPreferences.getString(SCREEN_INCHES, getString(R.string.Unknown)),
                    DisplayHelper.getRefreshValue(activity),
                    //DisplayHelper.getCurrentBrightness()
            };
        } else {
            stringValues = new String[] {
                    "1440x2880",
                    "538 dpi",
                    "6.00\"",
                    "60Hz"
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
