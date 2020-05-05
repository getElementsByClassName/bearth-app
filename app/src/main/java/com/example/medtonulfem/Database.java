
package com.example.medtonulfem;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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
    public Boolean saved;
    public ArrayList<User> users = new ArrayList<>();
    public ListView listView;
    public Context context;

    public Database( DatabaseReference databaseReference, ListView listView, Context context)
    {
        this.databaseReference = databaseReference;
        this.context = context;
        this.listView = listView;
    }

    public Database(){} //empty constructor

    /*
Retrieve and Return them clean data in an arraylist so that they just bind it to ListView.
 */
    public void retrieveUsersForScoreboard() {
        databaseReference.child("Users").orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Get User Objects and populate the arraylist.
                        User user = ds.getValue(User.class);
                        users.add(user);

                    }
                    System.out.println(users);
                    //orderByChild method shows the lowest points first, so reverse arraylist
                    Collections.reverse(users);
                    CustomAdapter adapter = new CustomAdapter(context, users);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //at database error, show error message to user
                Log.d("mTAG", databaseError.getMessage());
                Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    /*
    Method that add takes a points value, and adds it to the currents users (authenticated users) points value in the database
    */
    public void addPoints(final long points){

        //get database instance and current authenticated user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //create af reference to the current users points in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("points");

        //attach a single value eventlistener to the reference, so that it is run once and then detached
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                System.out.println(dataSnapshot.getValue());
                //retrieve current value
                long currentPoints = (long) dataSnapshot.getValue();

                //add the current value with the points value from function argument
                long newScore = currentPoints + points;

                //set the new points value at the reference (points for current user)
                databaseReference.setValue(newScore);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                Log.d("mTAG", databaseError.getMessage());
                Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    /*
    Method that checks if a authenticated users unique id is present in the JSON tree in the database, if not, then adds the user.
    All the users information are retrieved from the firebase authentication object.
    A user is only saved to the database once, at his/her first login/authentication
     */
    public void addUserToDatabase(){

        //get database instance and current authenticated user
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
                    User userSaveToDatabase = new User(currentUser.getDisplayName(), currentUser.getPhotoUrl().toString(), (long) 0);

                    //Set new value in users, with key as autenticated user Unique ID
                    databaseReference.setValue(userSaveToDatabase);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //at database error, show error message to user
                Log.d("mTAG", databaseError.getMessage());
                Toast.makeText(context, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }


    /*
    Method that returns the current users points, to show at profile page
    */
    public void retrieveUsersPoints(){}


    /*
    Method that updates the users avatar state, based on the current points
    */
    public void updateUsersAvatarState(){}



}
















/*
    public static void writeUserToDatabase(){

        //FirebaseAuth mAuth;
        //get database instance and current user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //Create reference to database users
        //final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();


                    mDatabase.child("name").setValue(user.getDisplayName());
                    mDatabase.child("photourl").setValue(user.getPhotoUrl().toString());
                    mDatabase.child("points").setValue(0);
                    System.out.println("USer DOes NOT exists");

*/
/*                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        //Data data = readData.getValue(Data.class);
                        //title.setText(data.getTitle());
                        //body.setText(data.getContent());
                    }*//*


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(FirebaseActivity11.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println("Database problem");
            }
        });
    }
}
*/





