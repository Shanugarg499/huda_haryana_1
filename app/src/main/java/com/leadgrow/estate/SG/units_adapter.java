package com.leadgrow.estate.SG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leadgrow.estate.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.StrictMath.round;

public class units_adapter extends RecyclerView.Adapter<units_adapter.Viewholder>{

    private ArrayList<Double> factors = new ArrayList<>();
    private ArrayList<String> areas = new ArrayList<>();
    private ArrayList<String> units = new ArrayList<>();
    private double from;
    private static DecimalFormat df = new DecimalFormat("0.0000");
    private static DecimalFormat df2 = new DecimalFormat("0.000000");
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
        if(((factors.get(position)*from)/smarty)>3000){
        holder.value.setText(String.valueOf(round((factors.get(position) * from)/smarty)));}
        else{if(((factors.get(position)*from)/smarty)<1)holder.value.setText(String.valueOf(df2.format(((factors.get(position)*from)/smarty))));
        else{holder.value.setText(String.valueOf(df.format((factors.get(position)*from)/smarty)));}
        }
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
