package com.cravingscravings.cravings;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;

/** MAIN FEED FRAGMENT */

public class MainFeedActivity extends AppCompatActivity {

    String currentUserId; // Storage of logged in user's ID
    String fname;
    String lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main_feed);

        // Setup Main Page using data taken from Facebook
        // Use profile setup from login page
        Profile profile = Profile.getCurrentProfile();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        currentUserId = profile.getId();
        fname = profile.getFirstName();
        lname = profile.getLastName();

        // If profile exists, login and open application fragments
        // If profile does not exist, logout and return to login field
        if (profile != null) {
            // Send request for user's friends list
            // Check if have user_friends permission
            Set<String> permissions = accessToken.getPermissions();
            if (!permissions.contains("user_friends")) {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends"));
            }

            // Goes to the user feed
            goToFeed();
        } else {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // Check if user exists, if not create user in database, then proceeds to main feed
    public void goToFeed() {
        class UserExistsTask extends AsyncTask<String, String, String> {
            @Override
            // Get JSON data on this thread
            protected String doInBackground(String... params) {
                HttpManager http = new HttpManager();
                String content = http.getData(params[0]);
                return content;
            }

            @Override
            protected void onPostExecute(String result) {
                // Display main feed if user exists, if not create new user
                if (result == null) {
                    Log.d("NOT EXIST", "ERROR");
                    createNewUser();
                }
                else {
                    ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
                    Log.d("pee", getSupportFragmentManager().toString());
                    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                }
            }
        }
        String uri = "https://dsp-wilbertthelam-53861.cloud.dreamfactory.com/rest/foomoo_db/users?ids=" + currentUserId + "&app_name=foomoo";
        Log.d("URI value",uri);
        UserExistsTask task = new UserExistsTask();
        task.execute(uri);
    }

    // Create new user in database
    private void createNewUser() {
        // Create JSON to POST
        JSONObject j = new JSONObject();
        try {
            j.put("user_id", currentUserId); // Set current user_id
            j.put("fname", fname); // Set current first name
            j.put("lname", lname); // Set current last name
            j.put("fav_1", "None"); // Set current fav 1
            j.put("fav_2", "None"); // Set current fav 2
            j.put("fav_3", "None"); // Set current fav 3
            j.put("current_craving", "Craving Anything!"); // Set current craving
            j.put("craving_timestamp", DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()); // Set current Craving Timestamp
        } catch (JSONException e) {
            e.printStackTrace();
        }

        class AddUserTask extends AsyncTask<String, String, String> {
            @Override
            // Before the AsyncTask is called, contains log info
            protected void onPreExecute() {
                Log.d("PRE-Executing", "success");
            }

            @Override
            // Get JSON data on this thread
            protected String doInBackground(String... params) {
                HttpManager http = new HttpManager();
                String result = http.addUser(params[0], params[1]);
                Log.d("asdf",result.toString());
                return result;
            }

            @Override
            // After the AsyncTask is called, moves feed data to UI thread.
            // Also includes log info
            protected void onPostExecute(String result) {
                Log.d("POST-Executing", result.toString());
                // If post success, go to main feed
                // Else logout
                if (result.equals("success")) {
                    ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
                    Log.d("NEW USER STATUS", "SUCCESSFUL");
                    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                }
                else {
                    Log.d("NEW USER STATUS", "CANNOT CREATE");
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(MainFeedActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        }

        String uri = "https://dsp-wilbertthelam-53861.cloud.dreamfactory.com:443/rest/foomoo_db/users/?app_name=foomoo";
        AddUserTask task = new AddUserTask();

        task.execute(uri, j.toString());
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
                case 1: return EventsListFragment.newInstance(currentUserId);
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