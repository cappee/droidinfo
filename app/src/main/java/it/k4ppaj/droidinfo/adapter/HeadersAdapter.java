package it.k4ppaj.droidinfo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.k4ppaj.droidinfo.R;

public class HeadersAdapter extends ArrayAdapter<String> {

    private String[] headers;
    private int[] icon;
    private Activity context;

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
        TextView textViewHeaders = (TextView) listViewItem.findViewById(R.id.textViewHeaders);
        ImageView imageViewIcon = (ImageView) listViewItem.findViewById(R.id.imageViewHeaders);

        textViewHeaders.setText(headers[position]);
        imageViewIcon.setImageResource(icon[position]);
        return  listViewItem;
    }
}
