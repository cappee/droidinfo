package it.k4ppaj.droidinfo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.k4ppaj.droidinfo.R;

public class BenchmarkAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] stringInformation;
    private String[] stringValues;

    public BenchmarkAdapter(@NonNull Activity context, String[] stringInformation, String[] stringValues) {
        super(context, R.layout.layout_benchmark, stringInformation);
        this.context = context;
        this.stringInformation = stringInformation;
        this.stringValues = stringValues;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemView = layoutInflater.inflate(R.layout.layout_benchmark, null, true);

        TextView textViewInformation = itemView.findViewById(R.id.textViewListBenchmarkInformation);
        TextView textViewValues = itemView.findViewById(R.id.textViewListBenchmarkValues);

        textViewInformation.setText(stringInformation[position]);
        textViewValues.setText(stringValues[position]);

        return itemView;
    }
}
