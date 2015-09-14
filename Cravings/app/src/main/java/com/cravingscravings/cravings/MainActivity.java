package com.cravingscravings.cravings;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/*
 * Class for main page
 */
public class MainActivity extends AppCompatActivity {

    Set<String> permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Main Page using data taken from Facebook
        // Use profile setup from login page
        Profile profile = Profile.getCurrentProfile();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (profile != null) {
            ((TextView) findViewById(R.id.main_username)).setText(profile.getName());
            ((ProfilePictureView) findViewById((R.id.main_userpicture))).setProfileId(profile.getId());

            // Send request for user's friends list
            // First check if have user_friends permission
            permissions = accessToken.getPermissions();
            if (!permissions.contains("user_friends")) {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends"));
            }
            GraphRequest request = GraphRequest.newMyFriendsRequest(
                    accessToken,
                    new GraphRequest.GraphJSONArrayCallback() {
                        @Override
                        public void onCompleted(JSONArray result, GraphResponse graphResponse) {
                            // When request completes, if has results use to populate feed
                            if (result == null) {
                                Log.d("asdf", "null");
                            }
                            else {
                                try {
                                    String id_url = "https://dsp-wilbertthelam-53861.cloud.dreamfactory.com/rest/foomoo_db/users?app_name=foomoo&ids=";
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject obj = result.getJSONObject(i);
                                        id_url += obj.getString("id") + ",";
                                    }

                                    // Get other info from database corresponding to the Facebook id
                                    Log.d("id_url", id_url);
                                    if (isOnline()) {
                                        requestFeedData(id_url);
                                    } else {
                                        //Toast.makeText(this, "Non-Facebook Network isn't working!", Toast.LENGTH_LONG).show();
                                        Log.d("database-status","not connecting");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
            request.executeAsync();

            /*
             */
        }
        // If cannot get current profile, return to login page
        else {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // Open up new activity to search activity
    public void searchCraving(View v) {
        Intent i = new Intent(MainActivity.this, SearchCravingActivity.class);
        i.putExtra("CurrentCraving", R.string.craving);
        startActivity(i);
    }

    // Generates the list of friends on the feed
    private void generateFeed(List<Friend> feedList) {
        ProfileListItemAdapter peopleAdapter = new ProfileListItemAdapter(this, feedList);
        ListView peopleProfileList = (ListView) findViewById(R.id.main_peopleprofile_list);
        peopleProfileList.setAdapter(peopleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Gets the data from Feed task
    private void requestFeedData(String uri) {
        FeedTask task = new FeedTask();
        task.execute(uri);
    }

    // Checks to see if network is connected
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    // *******SENDS TEXT TO LOG**********
    private void displayText(String text) {
        Log.d("tag", text);
    }

    // Implement AsyncTask to have thread in background to get user
    // feed network from REST endpoint
    private class FeedTask extends AsyncTask<String, String, String> {

        @Override
        // Before the AsyncTask is called, contains log info
        protected void onPreExecute() {
            displayText("PRE-Executing");
        }

        @Override
        // Get JSON data on this thread
        protected String doInBackground(String... params) {
            String content = HttpManager.getData(params[0]);
            Log.d("asdf",content);
            return content;
        }

        @Override
        // After the AsyncTask is called, moves feed data to UI thread.
        // Also includes log info
        protected void onPostExecute(String result) {
            displayText("POST-Executing - " + result);

            List<Friend> friendList = FeedJSONParser.parseFeed(result);
            Log.d("x",friendList.get(0).getCurrentCraving());
            generateFeed(friendList);
        }
    }
}
