package app.droidinfo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.droidinfo.R;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Item> listItemAdapter;

    private String FONT = "FONT";

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewInformation, textViewValues;

        MyViewHolder(View view) {
            super(view);
            textViewInformation = view.findViewById(R.id.textViewListInformation);
            textViewValues = view.findViewById(R.id.textViewListValues);
        }
    }

    public RecyclerViewAdapter(List<Item> listItemAdapter, Context context) {
        this.listItemAdapter = listItemAdapter;
        this.context = context;
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

        Item item = this.listItemAdapter.get(position);
        holder.textViewInformation.setTypeface(typeface);
        holder.textViewInformation.setText(item.getTitle());
        holder.textViewValues.setTypeface(typefaceBold);
        holder.textViewValues.setText(item.getSummary());
    }

    @Override
    public int getItemCount() {
        return listItemAdapter.size();
    }
}