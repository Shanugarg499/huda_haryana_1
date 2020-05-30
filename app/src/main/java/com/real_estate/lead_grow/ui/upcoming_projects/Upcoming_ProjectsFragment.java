package com.real_estate.lead_grow.ui.upcoming_projects;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.real_estate.lead_grow.R;

public class Upcoming_ProjectsFragment extends Fragment {

    private UpcomingProjectsViewModel mViewModel;

    public static Upcoming_ProjectsFragment newInstance() {
        return new Upcoming_ProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(UpcomingProjectsViewModel.class);
        return inflater.inflate(R.layout.upcoming__projects_fragment, container, false);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(UpcomingProjectsViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
