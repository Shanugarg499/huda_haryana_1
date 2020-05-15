package com.example.huda_haryana.SG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huda_haryana.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class units_adapter extends RecyclerView.Adapter<units_adapter.Viewholder>{

    private ArrayList<Integer> factors = new ArrayList<>();
    private ArrayList<String> areas = new ArrayList<>();
    private ArrayList<String> units = new ArrayList<>();

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_card, parent, false);
        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
