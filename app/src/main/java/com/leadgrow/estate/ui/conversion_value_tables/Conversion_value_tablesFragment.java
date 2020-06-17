package com.leadgrow.estate.ui.conversion_value_tables;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.leadgrow.estate.R;
import com.leadgrow.estate.SG.units_adapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Conversion_value_tablesFragment extends Fragment {

    private ConversionValueTablesViewModel mViewModel;
    ArrayList<Double> factors = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= ViewModelProviders.of(this).get(ConversionValueTablesViewModel.class);
        View root = inflater.inflate(R.layout.calculator_fragment, container, false);
        final TextView unitsel = root.findViewById(R.id.selectedunit);
        final Spinner spinner = root.findViewById(R.id.spinner);
        final EditText text = root.findViewById(R.id.entered_area);
        final RecyclerView rcv = root.findViewById(R.id.units_RCV);
        String unitsarray[] = getActivity().getResources().getStringArray(R.array.units);
        String regionarray[] = getActivity().getResources().getStringArray(R.array.regions);
        final ArrayList<String> units = new ArrayList<>(Arrays.asList(unitsarray));
        final ArrayList<String> regions = new ArrayList<>(Arrays.asList(regionarray));
        final int[] j = new int[1];

        final double[] Array = new double[]{ 1,0.001653,0.06689,0.005,0.002645,0.004132,0.008264,0.008889,0.006,0.007934, 0.052893
        , 0.082645, 0.165289, 0.177778, 0.158678, 6.689019, 0.165289, 0.4, 0.165289, 1.057851, 20, 0.001653, 0.03, 0.066116,
        0.000669, 0.013223, 0.025, 0.1, 0.052893, 0.12, 0.001653, 1, 0.016529, 0.5, 0.264463, 0.000066, 0.033333, 0.264463,
        0.006612, 2.380165, 0.165289, 72, 10368, 0.000006689018, 6.689018, 0.000003, 8};

        for(int i=0;i<47;i++){factors.add(Array[i]);}

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                j[0]=spinner.getSelectedItemPosition();
                unitsel.setText(spinner.getSelectedItem().toString());
            }

//            ArrayList<String> region = new ArrayList<>();



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        units_adapter adapter2 = new units_adapter(factors, regions, units, 0, 1);
        rcv.setAdapter(adapter2);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        root.findViewById(R.id.buttonconvert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.onEditorAction(EditorInfo.IME_ACTION_DONE);
                units_adapter adapter2 = new units_adapter(factors, regions, units, Double. parseDouble(text.getText().toString()), Array[j[0]]);
                rcv.setAdapter(adapter2);
                rcv.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        return root;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ConversionValueTablesViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
