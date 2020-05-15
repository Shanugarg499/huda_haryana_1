package com.example.huda_haryana.ui.conversion_value_tables;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huda_haryana.MainActivity;
import com.example.huda_haryana.R;

public class Conversion_value_tablesFragment extends Fragment {

    private ConversionValueTablesViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= ViewModelProviders.of(this).get(ConversionValueTablesViewModel.class);
        View root = inflater.inflate(R.layout.calculator_fragment, container, false);
        final TextView unitsel = root.findViewById(R.id.selectedunit);
        final Spinner spinner = root.findViewById(R.id.spinner);
        final EditText text = root.findViewById(R.id.entered_area);
        RecyclerView rcv = root.findViewById(R.id.units_RCV);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                unitsel.setText(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        root.findViewById(R.id.buttonconvert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getContext(), "Will be code if layout is fine", Toast.LENGTH_SHORT).show();
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
