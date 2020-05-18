package com.example.huda_haryana.SG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huda_haryana.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class units_adapter extends RecyclerView.Adapter<units_adapter.Viewholder>{

    private ArrayList<Double> factors = new ArrayList<>();
    private ArrayList<String> areas = new ArrayList<>();
    private ArrayList<String> units = new ArrayList<>();
    private double from;
    private double smarty;

    public units_adapter(ArrayList<Double> factors, ArrayList<String> areas, ArrayList<String> units, double from, double smarty) {
        this.factors = factors;
        this.areas = areas;
        this.units = units;
        this.from = from;
        this.smarty = smarty;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_card, parent, false);
        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.main.setText(units.get(position));
        holder.side.setText(areas.get(position));
        holder.value.setText(String.valueOf((factors.get(position) * from)/smarty));
    }

    @Override
    public int getItemCount() {
        return factors.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView main, side, value;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            main = itemView.findViewById(R.id.main);
            side = itemView.findViewById(R.id.side);
            value = itemView.findViewById(R.id.value);
        }
    }
}
