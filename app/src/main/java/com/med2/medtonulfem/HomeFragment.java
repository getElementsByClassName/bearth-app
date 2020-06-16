package com.med2.medtonulfem;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MaterialCardView.OnClickListener
{
    private FirebaseAuth mAuth;
    private String name = "Home";
    private TaskShowerFragment taskShowerFragment;
    private TaskPowerFragment taskPowerFragment;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    //return the name for this fragment, used in bottom navigation
    public String getName()
    {
        return name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //reference progressbar
        ProgressBar progressBarHome = view.findViewById(R.id.progressBarHome);

        //instaciate the two task fragments, that can be loaded from this fragment
        taskShowerFragment = new TaskShowerFragment();
        taskPowerFragment = new TaskPowerFragment();

        //set earth avatar image source
        final ImageView earthAvatarHome = view.findViewById(R.id.earthAvatarHome);

        //set bearth text status
        final TextView bearthStatus = view.findViewById(R.id.earthStatus);

        //create new database
        Database database = new Database();


        //init a shared preferences object (that holds if tasks is cleared)
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //reference topbar (for setting navigation title, when tasked clicked)
        final MaterialToolbar topBar = ((ContainerActivity) getActivity()).findViewById(R.id.topAppBar);

        //reference cards
        final MaterialCardView cardShower = view.findViewById(R.id.cardShower);
        final MaterialCardView cardElectricity = view.findViewById(R.id.cardElectricity);

        //get boolean value
        Boolean daily_task = sharedpreferences.getBoolean("task_daily", false);
        Boolean power_task = sharedpreferences.getBoolean("task_power", false);

        if(daily_task){
            cardShower.setChecked(true);
        }

        if(power_task){
            cardElectricity.setChecked(true);
        }



        //when click on shower card (default daily task)
        cardShower.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                ((ContainerActivity) getActivity()).setFragment(taskShowerFragment);
                topBar.setTitle(taskShowerFragment.getName());
            }
        });

        //when click on power task card (first bonus task)
        cardElectricity.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                ((ContainerActivity) getActivity()).setFragment(taskPowerFragment);
                topBar.setTitle(taskPowerFragment.getName());
            }
        });





        //set avatar based on database value
        database.readUsersAvatarStatus(earthAvatarHome, bearthStatus, progressBarHome);











       /* //sharedpreferences.edit().putInt("daily_score", 10).apply();

        int daily_score = sharedpreferences.getInt("daily_score", 0);

        int new_score = daily_score + 10;

        System.out.println(new_score);

        sharedpreferences.edit().putInt("daily_score", new_score).commit();

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        System.out.println(date);

        sharedpreferences.edit().putInt(date.toString(), 100000).apply();

        //int test = sharedpreferences.getInt(date.toString(), 0);

        //int test = sharedpreferences.getInt("test", 0);

        //System.out.println(test);
        */










        //set image on task
       // final ImageView iconTaskShow = view.findViewById(R.id.iconTaskShower);

     /*   showerTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //update with 250 points
                database.writePoints(250, "happy");

                //iconTaskShow.setImageResource(R.drawable.avatar_happy);


                //set daily task cleared
                sharedpreferences.edit().putBoolean("daily_task", true).apply();

                //set shower task cleared
                sharedpreferences.edit().putBoolean("shower_task", true).apply();

                //set new avatar
                //database.readUsersAvatarStatus(earthAvatarHome, bearthStatus);

                //update avatar status
                //database.updateUsersAvatarState("happy");
            }
        });*/
    } //end onViewCreated

    @Override
    public void onClick(View view) {

        System.out.println("Card pressed: " + view.getId());
       /* switch (view.getId()) {
            case R.id.button_home:
                Intent intent_home = new Intent(this,HomePage.class);
                intent_home.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_home);
                break;

            case R.id.button_profile:
                Intent intent_profile = new Intent(this,Profile.class);
                intent_profile.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_profile);
                break;

            case R.id.button_tasks:
                Intent intent_tasks = new Intent(this,Tasks.class);
                intent_tasks.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_tasks);
                break;

            case R.id.button_friends:
                Intent intent_friends = new Intent(this,Friends.class);
                intent_friends.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_friends);
                break;

            case R.id.button_change:
                Intent intent_start_task = new Intent(this,Task_Dry.class);
                intent_start_task.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_start_task);
                break;*/
    }
}
