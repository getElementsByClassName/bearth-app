package com.med2.medtonulfem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import androidx.fragment.app.Fragment;

public class TaskShowerFragment extends Fragment
{

    private String name = "Task > Shower";

    private Chronometer chronometer;
    private boolean     running;
    private Button      startButton;
    private Button      stopButton;
    private Button      doneButton;
    private long        timeBathing;
    private long points;
    private SharedPreferences sharedpreferences;
    private HomeFragment homeFragment;


    public TaskShowerFragment()
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
        return inflater.inflate(R.layout.fragment_taskshower, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        homeFragment = new HomeFragment();



        //init a shared preferences object
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //create new database
        final Database database = new Database();

        startButton = view.findViewById(R.id.startChronometer);
        stopButton  = view.findViewById(R.id.stopChronometer);
        chronometer = view.findViewById(R.id.chronometer);
        doneButton = view.findViewById(R.id.btnShowerComplete);
        final MaterialToolbar topBar = ((ContainerActivity) getActivity()).findViewById(R.id.topAppBar);


        waterTask();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean task_shower = sharedpreferences.getBoolean("task_daily", false);


                if(task_shower){
                    Toast.makeText(getActivity(),   "Denne opgave er løst for i dag", Toast.LENGTH_LONG).show();
                    System.out.println("allerede klaret");
                }else{
                    database.writePoints(points, "happy");
                    Toast.makeText(getActivity(),   "Du fik tilføjet " + points + " point", Toast.LENGTH_LONG).show();

                }
                //Set daily tasked to solved
                sharedpreferences.edit().putBoolean("task_daily", true).apply();

                //set earned badge to true
                sharedpreferences.edit().putBoolean("badge_water", true).apply();

                //change fragment
                ((ContainerActivity) getActivity()).setFragment(homeFragment);
                topBar.setTitle(homeFragment.getName());



            }
        });


    }


    public void waterTask(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    timeBathing = (long) 0.0000;
                    running = true;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    chronometer.stop();
                    timeBathing = SystemClock.elapsedRealtime() - chronometer.getBase();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    running = false;

                    sharedpreferences.edit().putBoolean("task_daily", false).apply();

                    if(timeBathing > 0 && timeBathing < 15 * 1000){
                        points = 250;
                        //database.writePoints(points, "Happy");
                        System.out.println("Shower task time " + points);
                    }else if(timeBathing >  15 * 1000 && timeBathing < 30 * 1000){
                        points = 100;
                        //database.writePoints(points, "Happy");
                    }else if(timeBathing > 30 * 1000 && timeBathing < 60 * 1000){
                        points = 50;
                        //database.writePoints(points, "Happy");
                    }
                }
            }
        });








    }

}