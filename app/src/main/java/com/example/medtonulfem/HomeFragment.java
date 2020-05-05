package com.example.medtonulfem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{
    private FirebaseAuth mAuth;
    private String name = "Home";

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

        //get the FirebaseAuth instance (singleton design)
        mAuth = FirebaseAuth.getInstance();

        //The object user now holds the information retrieved from facebook
        FirebaseUser user = mAuth.getCurrentUser();

        //get reference to text view and initialize greeting message
        TextView txt_title = view.findViewById(R.id.welcomeMessage);
        txt_title.setText("Welcome " + user.getDisplayName());

        //get reference to image view
        ImageView profilePicture = view.findViewById(R.id.profileImage);

        //retrieve url for profileimage, and set a height parameter
        String photoUrl = user.getPhotoUrl().toString();
        photoUrl = photoUrl + "?height=500";

        //use the library picasso to make a web request for the image and transform it (crop to circel)
        //set the content of the web request to the image view
        Picasso.get().load(photoUrl)
                .transform(new CropCircleTransformation()).into(profilePicture);


    }
}
