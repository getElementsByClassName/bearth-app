package com.example.medtonulfem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    private String name = "Profile";

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    public String getName()
    {
        return name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //Code for a snackbar
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Profile page", Snackbar.LENGTH_LONG);
        //snackBar.show();

    }


}


/*
    View contextView = findViewById(R.id.context_view);

Snackbar.make(contextView, R.string.item_removed_message, Snackbar.LENGTH_SHORT)
        .show();*/
