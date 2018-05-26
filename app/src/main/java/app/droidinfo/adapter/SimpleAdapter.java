package app.droidinfo.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import app.droidinfo.R;

import static android.content.Context.MODE_PRIVATE;

public class SimpleAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] stringInformation;
    private String[] stringValues;

    private String FONT = "FONT";

    public SimpleAdapter(@NonNull Activity context, String[] stringInformation, String[] stringValues) {
        super(context, R.layout.layout_listview, stringInformation);
        this.context = context;
        this.stringInformation = stringInformation;
        this.stringValues = stringValues;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemView = layoutInflater.inflate(R.layout.layout_listview, null, true);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", MODE_PRIVATE);

        Typeface typeface;

        if (sharedPreferences.getString(FONT, "Roboto").equals("Google Sans")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "GoogleSans" + ".ttf");
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + sharedPreferences.getString(FONT, "Roboto") + ".ttf");
        }

        Typeface typefaceBold;

        if (sharedPreferences.getString(FONT, "Roboto").equals("Google Sans")) {
            typefaceBold = Typeface.createFromAsset(context.getAssets(), "fonts/" + "GoogleSans" + "-Bold.ttf");
        } else {
            typefaceBold = Typeface.createFromAsset(context.getAssets(), "fonts/" + sharedPreferences.getString(FONT, "Roboto") + "-Bold.ttf");
        }

        TextView textViewInformation = itemView.findViewById(R.id.textViewListInformation);
        TextView textViewValues = itemView.findViewById(R.id.textViewListValues);

        textViewInformation.setTypeface(typeface);
        textViewValues.setTypeface(typefaceBold);

        textViewInformation.setText(stringInformation[position]);
        textViewValues.setText(stringValues[position]);

        return itemView;
    }
}
