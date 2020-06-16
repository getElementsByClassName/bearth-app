package com.med2.medtonulfem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private static final String TAG = "LOGIN LOG";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth object, handles authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize a facebook API callback manager (handles login result under the hood)
        mCallbackManager = CallbackManager.Factory.create();

        // Initialize Facebook Login button
        final LoginButton loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email", "public_profile"); //check setReadPermissins, deprecated?

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //render facebook login button to gone
                loginButton.setVisibility(View.GONE);

                //call firebase function to handle login result via facebook
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // if facebook login fails, send message to user
            }

            @Override
            public void onError(FacebookException error) {
                //if an error occurs
            }
        });

    }//end onCreate()



    // Google API, called when facebook sign succeeded, now authenticate with google firebase
    private void handleFacebookAccessToken(AccessToken token) {

        //instanciate a credential object (contains facebook token)
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        //mAuth is an authentication object
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {

                            // display progressbar
                            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.VISIBLE);


                            //init a shared preferences object (that holds if tasks is cleared)
                            sharedpreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                            //set tasks to un-checked
                            sharedpreferences.edit().putBoolean("task_daily", false).apply();
                            sharedpreferences.edit().putBoolean("task_power", false).apply();
                            sharedpreferences.edit().putBoolean("badge_water", false).apply();

                            Boolean badge_water = sharedpreferences.getBoolean("badge_water", false);

                            System.out.println("Badge water: " + badge_water);


                            //instanciate current user
                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            //check if user is registered in database (and save if not)
                            Database database = new Database();
                            database.addUserToDatabase();

                            //use write points to reset status to 'dry'
                            //database.writePoints(0, "dry");

                            //call update UI method (intent to ContainerActivity)
                            updateUI(currentUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    //on activity lifecycle method onStart, checks if user is authenticated, if ok, update UI (go to container activity)
    @Override
    public void onStart() {
        super.onStart();
        //get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if user is signed in (not-null) and update UI accordingly.
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    //method is passed the current user (authenticated firebase user)
    private void updateUI(FirebaseUser user)
    {
        Toast.makeText(MainActivity.this, "You are logged in" + user.getDisplayName(), Toast.LENGTH_LONG).show();

        //start container activity, that holds the bottom navigation bar, where all fragments are swapped in
        Intent intent = new Intent(MainActivity.this, ContainerActivity.class); //explicit intent, specific class context and specific class
        startActivity(intent);
        finish();

    }

    //inside onActivityResult the facebook callbackmanager gets the Facebook login result code (if login succeeded)
    //if login code is OK, the result is handle in the onSuccess method line 88
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
