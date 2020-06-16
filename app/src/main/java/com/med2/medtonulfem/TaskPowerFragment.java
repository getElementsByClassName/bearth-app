package com.med2.medtonulfem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import androidx.fragment.app.Fragment;

public class TaskPowerFragment extends Fragment implements View.OnClickListener
{

    private String name = "Task > Spar på strømmen";
    private int point = 20;
    private int sumPoints;
    private HomeFragment homeFragment;
    private SharedPreferences sharedpreferences;
    private Button doneButton;


    public TaskPowerFragment()
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
        return inflater.inflate(R.layout.fragment_taskpower, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        //reference home fragment (the fragment shown, when done button pressed)
        homeFragment = new HomeFragment();

        //init a shared preferences object
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //create new database
        final Database database = new Database();

        //reference topbar
        final MaterialToolbar topBar = ((ContainerActivity) getActivity()).findViewById(R.id.topAppBar);


        //reference checkboxes
        CheckBox check_tv       = view.findViewById(R.id.check_tv);
        CheckBox check_charger  = view.findViewById(R.id.check_charger);
        CheckBox check_computer = view.findViewById(R.id.check_computer);
        CheckBox check_games    = view.findViewById(R.id.check_game);
        CheckBox check_light    = view.findViewById(R.id.check_light);
        CheckBox check_kitchen  = view.findViewById(R.id.check_kitchen);
        CheckBox check_bathroom = view.findViewById(R.id.check_bathroom);

        //reference done button
        doneButton = view.findViewById(R.id.btnPowerComplete);

        //set click listeners
        check_tv.setOnClickListener(this);
        check_charger.setOnClickListener(this);
        check_computer.setOnClickListener(this);
        check_games.setOnClickListener(this);
        check_light.setOnClickListener(this);
        check_kitchen.setOnClickListener(this);
        check_bathroom.setOnClickListener(this);

        //when click on done button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean task_power = sharedpreferences.getBoolean("task_power", false);
                Boolean task_daily = sharedpreferences.getBoolean("task_daily", false);


                if(task_power){
                    Toast.makeText(getActivity(),   "Denne opgave er løst for i dag", Toast.LENGTH_LONG).show();
                }else{
                    if(task_daily){
                        database.writePoints(sumPoints, "winner");
                    }else{
                        database.writePoints(sumPoints, "happy");
                    }

                    Toast.makeText(getActivity(),   "Du fik tilføjet " + sumPoints + " point", Toast.LENGTH_LONG).show();
                }

                //Set power task to solved
                sharedpreferences.edit().putBoolean("task_power", true).apply();

                //change fragment
                ((ContainerActivity) getActivity()).setFragment(homeFragment);
                topBar.setTitle(homeFragment.getName());
            }
        });

    }

    @Override
    public void onClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.check_tv:
            case R.id.check_charger:
            case R.id.check_computer:
            case R.id.check_game:
            case R.id.check_light:
            case R.id.check_kitchen:
            case R.id.check_bathroom:
                if(checked) {
                    sumPoints += point;
                    //MainActivity.user.setPoint(point);
                } else {
                    sumPoints -= point;
                    //MainActivity.user.removePoint(point);
                }
                break;
        }
    }

}
