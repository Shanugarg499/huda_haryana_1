package com.example.huda_haryana.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.huda_haryana.LabelsFrag.LabelFrag;
import com.example.huda_haryana.R;
import com.example.huda_haryana.fragments_bottom.TaskFragment;
import com.example.huda_haryana.fragments_bottom.ab.LeadsPageUpdated;
import com.example.huda_haryana.mybusiness.BusinessFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new LeadsPageUpdated()).commit();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_leads:
                            selectedFragment = new LeadsPageUpdated();
                            break;
                        case R.id.nav_labels:
                            selectedFragment = new LabelFrag();
                            break;
                        case R.id.nav_tasks:
                            selectedFragment = new TaskFragment();
                            break;
                        case R.id.nav_business:
                            selectedFragment = new BusinessFrag();
                            break;
                    }
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
