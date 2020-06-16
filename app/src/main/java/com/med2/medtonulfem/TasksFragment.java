package com.med2.medtonulfem;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment
{

    private String name = "Tasks";
    private SharedPreferences sharedpreferences;

    private TaskShowerFragment taskShowerFragment;
    private TaskPowerFragment taskPowerFragment;

    public TasksFragment()
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
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //instanciate fragments that can be loaded from task fragment (spar på vandet og spar på strømmen)
        taskShowerFragment = new TaskShowerFragment();
        taskPowerFragment = new TaskPowerFragment();

        //init a shared preferences object
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //reference cards
        final MaterialCardView cardShower = view.findViewById(R.id.cardShower);
        final MaterialCardView cardElectricity = view.findViewById(R.id.cardElectricity);

        //get boolean values from sharedprferences (if tasked is cleared)
        Boolean daily_task = sharedpreferences.getBoolean("task_daily", false);
        Boolean power_task = sharedpreferences.getBoolean("task_power", false);

        //check is tasks is cleared, then add checked mark
        if(daily_task){
            cardShower.setChecked(true);
        }

        if(power_task){
            cardElectricity.setChecked(true);
        }

        //reference topbar, to set navigation title
        final MaterialToolbar topBar = ((ContainerActivity) getActivity()).findViewById(R.id.topAppBar);

        //when click on shower task card (spar på vandet)
       cardShower.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ((ContainerActivity) getActivity()).setFragment(taskShowerFragment);
                topBar.setTitle(taskShowerFragment.getName());
            }
        });

        //when click on power task card (spar på strømmen)
        cardElectricity.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                ((ContainerActivity) getActivity()).setFragment(taskPowerFragment);
                topBar.setTitle(taskPowerFragment.getName());
            }
        });





    }


    }


