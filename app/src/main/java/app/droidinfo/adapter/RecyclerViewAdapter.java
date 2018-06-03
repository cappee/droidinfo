package app.droidinfo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.droidinfo.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private String[] stringInformation, stringValues;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewInformation, textViewValues;

        MyViewHolder(View view) {
            super(view);
            textViewInformation = view.findViewById(R.id.textViewListInformation);
            textViewValues = view.findViewById(R.id.textViewListValues);
        }
    }


    public RecyclerViewAdapter(String[] stringInformation, String[] stringValues) {
        this.stringInformation = stringInformation;
        this.stringValues = stringValues;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewInformation.setText(stringInformation[position]);
        holder.textViewValues.setText(stringValues[position]);
    }

    @Override
    public int getItemCount() {
        return stringInformation.length;
    }
}