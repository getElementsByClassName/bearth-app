package com.med2.medtonulfem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.facebook.login.LoginManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
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

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        //init a shared preferences object
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //sharedpreferences.edit().putBoolean("daily_task", false).apply();


        //initialize fragments
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        friendsFragment = new FriendsFragment();
        tasksFragment = new TasksFragment();

        //initialize frame layout (fragment container) and bottom navigation bar
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.nav_view);

        //set fragment to be viewed in frame layout (fragment_container) to be 'home fragment'
        setFragment(tasksFragment);

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




    /*
    * Method that sets a new fragment
    * */
    public void setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    private void logoutUser()
    {

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, MainActivity.class); //explicit intent, specific class context and specific class
        startActivity(intent);
        finish();
    }



    public void cardClicked(MaterialCardView cardView) {

        int id = cardView.getId();
        System.out.println("Card clicked id is: " + id);
        cardView.isChecked();
        //final MaterialCardView cardView = findViewById(R.id.card);
        //view.setC
   /*     switch (id){
            case R.id.button1:
                //do ur code
                Toast.makeText(this,"This is button 1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Intent intent = new Intent(this,SecondActivity.class);
                Intent.putExtra("key",value);
                startActivity(intent);
                break;
            case R.id.button3:
                //do ur code
                break;
            case R.id.button4:
                //do ur code;
                break;
            default:
                //do ur code;
        }*/

      /*  if (view.getId() == R.id.button1) {
            // button1 action
        } else if (view.getId() == R.id.button2) {
            //button2 action
        } else if (view.getId() == R.id.button3) {
            //button3 action
        }*/

    }



}
