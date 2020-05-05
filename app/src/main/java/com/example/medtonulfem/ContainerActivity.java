package com.example.medtonulfem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.facebook.login.LoginManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ContainerActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private BottomNavigationView bottomNavigation;
    private FrameLayout fragmentContainer;

    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FriendsFragment friendsFragment;
    private TasksFragment tasksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);



        //initialize fragments
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        friendsFragment = new FriendsFragment();
        tasksFragment = new TasksFragment();

        //initialize frame layout (fragment container) and bottom navigation bar
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.nav_view);

        //set fragment to be viewed in frame layout (fragment_container) to be 'home fragment'
        setFragment(homeFragment);

        //reference topbar, and set title (Home - fragment)
        final MaterialToolbar topBar = findViewById(R.id.topAppBar); // get the reference of Material Toolbar
        topBar.setTitle(homeFragment.getName());


        topBar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.exit)
                {
                    System.out.println("Exit");
                    logoutUser();
                }

                return false;
            }
        });


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        setFragment(homeFragment);
                        topBar.setTitle(homeFragment.getName());
                        return true;

                    case R.id.navigation_friends:
                        setFragment(friendsFragment);
                        topBar.setTitle(friendsFragment.getName());
                        return true;

                    case R.id.navigation_profile:
                        setFragment(profileFragment);
                        topBar.setTitle(profileFragment.getName());
                        return true;

                    case R.id.navigation_tasks:
                        setFragment(tasksFragment);
                        topBar.setTitle(tasksFragment.getName());
                        return true;

                    default:
                        return false;

                }

            }
        });



  /*      // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        TextView txt_title = (TextView) findViewById(R.id.welcomeMessage);
        txt_title.setText("Welcome " + user.getDisplayName());

        ImageView profilePicture = (ImageView) findViewById(R.id.profileImage);

        String photoUrl = user.getPhotoUrl().toString();
        photoUrl = photoUrl + "?height=500";

        Picasso.get().load(photoUrl).into(profilePicture);


        Button btnLogOut = (Button) findViewById(R.id.btn_LogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                LoginManager.getInstance().logOut();

                //btnGoToSecondActivity.setText("test");
                Intent intent = new Intent(v.getContext(), MainActivity.class); //explicit intent, specific class context and specific class
                startActivity(intent);
                finish();
            }
        });*/
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void logoutUser()
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        //btnGoToSecondActivity.setText("test");
        Intent intent = new Intent(this, MainActivity.class); //explicit intent, specific class context and specific class
        startActivity(intent);
        //finish();
    }
}
