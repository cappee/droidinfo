package it.k4ppaj.droidinfo.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.k4ppaj.droidinfo.R;

import static android.content.Context.MODE_PRIVATE;

public class HeadersAdapter extends ArrayAdapter<String> {

    private String[] headers;
    private int[] icon;
    private Activity context;

    private String FONT = "FONT";

    public HeadersAdapter(Activity context, String[] headers, int[] icon) {
        super(context, R.layout.layout_headers, headers);
        this.context = context;
        this.headers = headers;
        this.icon = icon;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_headers, null, true);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DroidInfo", MODE_PRIVATE);

        Typeface typeface;

        if (sharedPreferences.getString(FONT, "Roboto").equals("Google Sans")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "GoogleSans" + ".ttf");
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + sharedPreferences.getString(FONT, "Roboto") + ".ttf");
        }

        TextView textViewHeaders = listViewItem.findViewById(R.id.textViewHeaders);
        ImageView imageViewIcon = listViewItem.findViewById(R.id.imageViewHeaders);

        textViewHeaders.setTypeface(typeface);

        textViewHeaders.setText(headers[position]);
        imageViewIcon.setImageResource(icon[position]);
        return  listViewItem;
    }
}
