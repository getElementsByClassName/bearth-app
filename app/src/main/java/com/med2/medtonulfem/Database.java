
package com.med2.medtonulfem;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;


public class Database
{
    public FirebaseAuth mAuth;
    public DatabaseReference databaseReference;
    public ArrayList<User> users = new ArrayList<>();
    public ListView listView;
    public Context context;


    //constructor used when creating listview, listview adapter needs a context
    public Database(ListView listView, Context context)
    {
        this.context = context;
        this.listView = listView;
    }

    //empty constructor
    public Database(){}

    /*
Retrieve and Return the clean data in an arraylist so that they just bind to the ListView.
 */
    public void readUsersForScoreboardOrGetUserPosition(final TextView textView, final FirebaseUser currentUser, final ProgressBar progressBar) {

        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //render progressbar invinsible
                progressBar.setVisibility(View.GONE);

                //empty users arrayList
                users.clear();
                //if a not empty datasnapshot is retrieved
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                        //Get User Objects and populate the Users arraylist (type of Users).
                        User user = datasnapshot.getValue(User.class);
                        //System.out.println(user);
                        users.add(user);

                    }

                    //orderByChild method shows the lowest points first, so reverse arraylist
                    Collections.reverse(users);

                    //if this method is called with a textView and a Firebaseuser argument
                    if(textView != null && currentUser != null){

                        //loop through the users array
                        for (User user : users) {
                            //if a user in the array has a photourl that equals the current logged in user (photo url is unique)
                            if (user.getPhotourl().equals(currentUser.getPhotoUrl().toString())) {

                                //array index starts at 0, so add 1 to get leaderboard position
                                int position = users.indexOf(user) + 1;

                                //set the text content of the passed textView
                                textView.setText("You are #" + position);
                                break;

                            }
                        }

                    }

                    //if the method is called with no arguments (null, null), the array of users are used for creating a list view (with adapter)
                    if(textView == null && currentUser == null){

                        //construct new adapter, takes a context and the just generated arraylist of users
                        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(context, users);

                        //set listview to adapter
                        listView.setAdapter(leaderboardAdapter);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //at database error, show error message to user
                //Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    /*
    Method that add takes a points value, and adds it to the currents users (authenticated users) points value in the database
    */
    public void writePoints(final long points, final String avatarStatus){

        //get database instance and current authenticated user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //create af reference to the current users points in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        //attach a single value eventlistener to the reference, so that it is run once and then detached
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //databaseReference.child("avatarStatus").setValue("happy");
                //databaseReference.child("points").setValue(500);

                //long key = (long) databaseReference.child("points").getValue();
                //System.out.println("Key is: " + key);
                //retrieve current value
                long currentPoints = (long) dataSnapshot.child("points").getValue();

                //add the current value with the points value from function argument
                long newScore = currentPoints + points;

                //set the new points value at the reference (points for current user)
                //databaseReference.setValue(newScore);
                databaseReference.child("points").setValue(newScore);

                databaseReference.child("avatarStatus").setValue(avatarStatus);



                //System.out.println("Wrote points.........");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                //Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
    /*
    Method that checks if a authenticated users unique id is present in the JSON tree in the database, if not, then adds the user.
    All the current user's information are retrieved from the firebase authentication object.
    A user is only saved to the database once, at his/her first login/authentication
     */
    public void addUserToDatabase(){

        //get authentication object instance and current authenticated user
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        //create af reference to the Users tree in database at current users unique ID (Uid)
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        //attach a single value eventlistener to the reference, so that it is run once and then detached
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //if no datasnapshot exists in users with current users UID, user has to be added to database
                if(!dataSnapshot.exists()){
                    //create new user object with values from authenticated userobject
                    User userSaveToDatabase = new User("dry", currentUser.getDisplayName(), currentUser.getPhotoUrl().toString(), (long) 0);

                    //Set new value in users, with key as autenticated user Unique ID
                    databaseReference.setValue(userSaveToDatabase);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                //Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    /*
    Method that returns the current users points, to show at profile page
    */
    public void readPoints(final TextView textView){

        //get database instance and current authenticated user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //create af reference to the current users points in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("points");

        //attach a value eventlistener to the reference
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                System.out.println(dataSnapshot.getValue());
                //retrieve current value
                long currentPoints = (long) dataSnapshot.getValue();

                //when method is called, it's passed a textview, set the text content
                textView.setText(dataSnapshot.getValue().toString() + " points");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                //Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


 /*   *//*
    Method that updates the users avatar state, based on the current points
    *//*

    public void updateUsersAvatarState(final String avatarStatus){

        //get database instance and current authenticated user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //create af reference to the current users avatar status in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("avatarStatus");

        //attach a single value eventlistener to the reference, so that it is run once and then detached
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //databaseReference.setValue("happy");
                databaseReference.setValue(avatarStatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                //Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }*/


       /*
    Method that updates the users avatar state, both a text view and image view
    */

    public void readUsersAvatarStatus(final ImageView avatarView, final TextView bearthStatus, final ProgressBar progressBar){

        //get database instance and current authenticated user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //create af reference to the current users avatar status in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("avatarStatus");

        //attach a value eventlistener to the reference
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                //render progressbar invinsible
                progressBar.setVisibility(View.GONE);

                //retrieve status
                String avatarStatus = dataSnapshot.getValue().toString();

                //reference animation settings (xml file)
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(avatarView.getContext(), R.anim.fadein);

                switch(avatarStatus){
                    case "druged":
                        avatarView.setImageResource(R.drawable.avatar_druged);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: Drugged");
                        break;
                    case "dry":
                        avatarView.setImageResource(R.drawable.avatar_dry);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: Tørstig");
                        break;
                    case "happy":
                        avatarView.setImageResource(R.drawable.avatar_happy);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: Glad");
                        break;
                    case "heiss":
                        avatarView.setImageResource(R.drawable.avatar_heiss);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: Overophedet");
                        break;
                    case "trash":
                        avatarView.setImageResource(R.drawable.avatar_trash);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: Beskidt");
                        break;
                    case "very_dry":
                        avatarView.setImageResource(R.drawable.avatar_very_dry);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: Meget tørstig");
                        break;
                    case "winner":
                        avatarView.setImageResource(R.drawable.avatar_winner);
                        avatarView.startAnimation(myFadeInAnimation);
                        bearthStatus.setText("Bearth Status: I vinder zonen");
                        break;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                //Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}

















