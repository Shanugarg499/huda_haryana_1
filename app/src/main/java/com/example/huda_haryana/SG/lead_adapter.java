package com.example.huda_haryana.SG;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huda_haryana.Lead.LeadOptions;
import com.example.huda_haryana.R;
import com.example.huda_haryana.ask_details;

import java.util.ArrayList;

public class lead_adapter extends RecyclerView.Adapter<lead_adapter.Viewholder> {

    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> nos = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private Context context;

    public lead_adapter(ArrayList<String> names, ArrayList<String> nos, ArrayList<String> data, Context context) {
        this.names = names;
        this.data = data;
        this.nos = nos;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_card, parent, false);
        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
        holder.options.setVisibility(View.GONE);
        final int i =0;
        holder.tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){holder.options.setVisibility(View.VISIBLE);}
                if(i==1){holder.options.setVisibility(View.GONE);}
            }
        });
        holder.maintext.setText(names.get(position));
        holder.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mycard.setVisibility(View.GONE);
            }
        });
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This option of deletion will activate soon", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LeadOptions.class).putExtra("id",nos.get(position)).putExtra("name",names.get(position));
                holder.itemView.getContext().startActivity(intent);
//                Toast.makeText(context,nos.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView maintext;
        LinearLayout options, mycard;
        ImageView tools, hide, cross;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            maintext = itemView.findViewById(R.id.data);
            options = itemView.findViewById(R.id.options);
            tools = itemView.findViewById(R.id.tools);
            hide = itemView.findViewById(R.id.hide);
            mycard = itemView.findViewById(R.id.my_card);
            cross = itemView.findViewById(R.id.cross);
        }
    }
}
