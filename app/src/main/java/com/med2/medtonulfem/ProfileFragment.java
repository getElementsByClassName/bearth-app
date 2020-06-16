package com.med2.medtonulfem;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    private String name = "Profile";

    private FirebaseAuth mAuth;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

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

        //init a shared preferences object
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //referenre progressbar
        ProgressBar progressBarProfile = view.findViewById(R.id.progressBarProfile);

        //get the FirebaseAuth instance (singleton design)
        mAuth = FirebaseAuth.getInstance();

        //The object user now holds the information retrieved from facebook
        FirebaseUser user = mAuth.getCurrentUser();

        //get reference to text view and initialize greeting message
        TextView txt_title = view.findViewById(R.id.userName);
        txt_title.setText(user.getDisplayName());

        //get reference to image view
        ImageView profilePicture = view.findViewById(R.id.profileImage);

        //retrieve url for profileimage, and set a height parameter
        String photoUrl = user.getPhotoUrl().toString();
        photoUrl = photoUrl + "?height=500";

        //use the library picasso to make a web request for the image and transform it (crop to circel)
        //set the content of the web request to the image view
        Picasso.get().load(photoUrl)
                .transform(new CropCircleTransformation()).into(profilePicture);


        //get reference to text view showing points
        TextView txt_points = view.findViewById(R.id.userPoints);

        //get reference to text view showing leaderboard position
        TextView txt_leaderboardPosition = view.findViewById(R.id.userPosition);

        //create new database object
        Database database = new Database();

        //use method that read points (eventlistener), and pass the textview that should hold the points
        database.readPoints(txt_points);

        //reference earth avatar image source
        ImageView earthAvatar = view.findViewById(R.id.earthAvatar);

        //set bearth text status
        final TextView bearthStatusProfile = view.findViewById(R.id.bearthaProfileStatus);

        //set avatar based on database value
        database.readUsersAvatarStatus(earthAvatar, bearthStatusProfile, progressBarProfile);


        //read position
        database.readUsersForScoreboardOrGetUserPosition(txt_leaderboardPosition, user, progressBarProfile);

        //set water badge to earned (if earned)
        Boolean badge_water = sharedpreferences.getBoolean("badge_water", false);


        if(badge_water){
            ImageView water_badge = view.findViewById(R.id.button_badge_water);

            //reference animation settings (xml file)
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
            water_badge.setImageResource(R.drawable.badges_water);
            water_badge.startAnimation(myFadeInAnimation);

            Toast.makeText(getActivity(),   "Du har optjent et badge! Water Slayer", Toast.LENGTH_LONG).show();
        }

        //use android shared preference
        //SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = pref.edit();



        //edit shared preferences object (editor now references sharedpreferences.edit() method)
        //editor = sharedpreferences.edit();
        //sharedpreferences.edit().putInt("daily_score", 10).apply();

        int daily_score = sharedpreferences.getInt("daily_score", 0);

        System.out.println(daily_score);


  /*      //editor.putString("key_name", "string value");
        editor.putInt("daily_points", 0);

        int daily_points = pref.getInt("daily_points", 0);

        //add to daily points
        int new_points = 10 + daily_points;

        editor.putInt("daily_points", new_points);*/


/*        // Save the changes in SharedPreferences
        editor.apply(); // commit changes

        String email=pref.getString("key_name", null);
        int retrieve_points = pref.getInt("daily_points", 0);

        System.out.println(retrieve_points);

        //Code for a snackbar
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Profile page", Snackbar.LENGTH_LONG);
        //snackBar.show();*/

    }


}


/*
    View contextView = findViewById(R.id.context_view);

Snackbar.make(contextView, R.string.item_removed_message, Snackbar.LENGTH_SHORT)
        .show();*/
