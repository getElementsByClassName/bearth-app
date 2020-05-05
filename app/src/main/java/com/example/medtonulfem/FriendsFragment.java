package com.example.medtonulfem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment
{

    private String name = "Friends";
    private ListView listViewScoreboard;
    //private ArrayAdapter arrayAdapter;
    //private FirebaseListAdapter adapter;

    //String[] data = new String[]{"Hans", "Morten", "Sara", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};



    public FriendsFragment()
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
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        listViewScoreboard = (ListView) view.findViewById(R.id.listViewScoreboard);
        //initialize firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //construct database object, that connects to firebase
        Database database = new Database(databaseReference, listViewScoreboard, getContext());

        //use method for populating scoreboard
        database.retrieveUsersForScoreboard();

        //add points
        database.addPoints(5);


/*
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference()
                .child("Users");

        listView = view.findViewById(R.id.listView);
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Users");
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setLayout(R.layout.single_list_row)
                .setQuery(query, User.class)
                .build();

        FirebaseListAdapter<User> adapter = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(View v, User model, int position) {
                // Bind the Chat to the view
                // ...
                //position = getContext().fin
                System.out.println(position);
                TextView name = v.findViewById(R.id.rowTextView);
                User user = (User) model;
                name.setText(user.getName());

            }


        };

        listView.setAdapter(adapter);
        adapter.startListening();
*/


/*        // Now set the adapter with a given layout
        listView.setAdapter(new FirebaseListAdapter<User>(getActivity(), User.class,
                , userReference) {

            // Populate view as needed
            @Override
            protected void populateView(View view, User user, int position) {
                //(view.findViewById(android.R.id.text1)).setText(user.getName());
                TextView name = view.findViewById(R.id.rowTextView);
            }
        });*/
 /*       super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        listView.setScrollContainer(false);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, data);

        if(listView != null){
            listView.setAdapter(arrayAdapter);
        }*/

    }


}
