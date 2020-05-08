package com.example.huda_haryana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ask_details extends AppCompatActivity {

    Button SubmitButton;
    int i = 0;
    TextView t4;
    EditText t1, t2, t5, t6, t7, t8, t9;
    Switch t3;
    private ArrayList<String> nos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_details);
        final DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("leads");


        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nos.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    order_to_database info = snapshot.getValue(order_to_database.class);
                    if(info!=null){
                        nos.add(info.getNumber());
                    }
                    i = nos.size()+1;

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ask_details.this, "Data load failed", Toast.LENGTH_SHORT).show();
            }
        });


        t3 = findViewById(R.id.switch1);
        t4 = findViewById(R.id.Type);
        t1 = findViewById(R.id.name);
        t2 = findViewById(R.id.searchingat);
        t5 = findViewById(R.id.specifically);
        t6 = findViewById(R.id.cont_no);
        t7 = findViewById(R.id.email_);
        t8 = findViewById(R.id.address);
        t9 = findViewById(R.id.event);


        findViewById(R.id.Type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ask_details.this);

                t3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Toast.makeText(ask_details.this, "Dialog will appear", Toast.LENGTH_SHORT).show();
                        if (isChecked) {

                            ((Dialog)dialog).setContentView(R.layout.sg_dialog_commercial);
                            TextView shop = (TextView)((Dialog)dialog).findViewById(R.id.shop_txt);
                            TextView sco = (TextView)((Dialog)dialog).findViewById(R.id.sco_txt);
                            TextView scf = (TextView)((Dialog)dialog).findViewById(R.id.scf_txt);
                            TextView oth = (TextView)((Dialog)dialog).findViewById(R.id.com_others_txt);

                            shop.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("Shops");
                                }
                            });

                            sco.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("SCOs");

                                }
                            });

                            scf.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("SCFs");
                                }
                            });

                            oth.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("Others");
                                }
                            });
                        } else {

                            ((Dialog)dialog).setContentView(R.layout.residentials);
                            TextView shop = (TextView)((Dialog)dialog).findViewById(R.id.plot_txt);
                            TextView sco = (TextView)((Dialog)dialog).findViewById(R.id.flat_txt);
                            TextView scf = (TextView)((Dialog)dialog).findViewById(R.id.villa_txt);
                            TextView oth = (TextView)((Dialog)dialog).findViewById(R.id.resi_others_txt);

                            shop.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("Plots");
                                }
                            });

                            sco.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("Flats");

                                }
                            });

                            scf.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("Villa");
                                }
                            });

                            oth.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    t4.setText("Others");
                                }
                            });
                        }
                    }
                });
            }
        });

        SubmitButton = findViewById(R.id.submitbutton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!t4.getText().toString().equals("--Select--") && !t1.getText().toString().equals("") && !t6.getText().toString().equals("") && !t7.getText().toString().equals(""))
                {
                    String txt = t2.getText().toString()+"."+t4.getText().toString()+"."+
                            t5.getText().toString()+"."+t6.getText().toString()+"."+t7.getText().toString()+"."+t8.getText().toString()+"."+
                            t9.getText().toString();
                    Toast.makeText(ask_details.this, "Successfully data stored", Toast.LENGTH_SHORT).show();
                    String t = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    dbr.child(t).setValue(new order_to_database(t1.getText().toString(), t2.getText().toString(),
                            t3.getText().toString(), t4.getText().toString(), t5.getText().toString(), t6.getText().toString(),
                            t7.getText().toString(), t8.getText().toString(), t9.getText().toString(), t,new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault()).format(new Date()),  t));
                    Toast.makeText(ask_details.this, "Recorded successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ask_details.this, "Sorry! can't store without complete details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
