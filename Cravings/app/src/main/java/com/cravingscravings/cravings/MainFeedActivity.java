package com.cravingscravings.cravings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.util.Set;

public class MainFeedActivity extends AppCompatActivity {

    String currentUserId; // Storage of logged in user's ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        // Setup Main Page using data taken from Facebook
        // Use profile setup from login page
        Profile profile = Profile.getCurrentProfile();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        currentUserId = profile.getId();

        // If profile exists, login and open application fragments
        // If profile does not exist, logout and return to login field
        if (profile != null) {
            // Send request for user's friends list
            // Check if have user_friends permission
            Set<String> permissions = accessToken.getPermissions();
            if (!permissions.contains("user_friends")) {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends"));
            }

            ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
            Log.d("pee", getSupportFragmentManager().toString());
            pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        } else {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // Adapter to manage through the MainFeed Fragment and the Event Fragment
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        // Set the positions of the fragments.
        // Default set to the Main Feed
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return MainFeedActivityFragment.newInstance(currentUserId);
                case 1: return EventActivityFragment.newInstance(currentUserId);
                default: return MainFeedActivityFragment.newInstance(currentUserId);
            }
        }

        @Override
        // Return number of fragments in Activity
        public int getCount() {
            return 2;
        }
    }


    // Open up new activity to search activity
    public void searchCraving(View v) {
        Intent i = new Intent(this, SearchCravingActivity.class);
        i.putExtra("CurrentCraving", R.string.craving);
        i.putExtra("CurrentUserID", currentUserId);
        startActivity(i);
    }
}