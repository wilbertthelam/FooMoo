package com.cravingscravings.cravings;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFeedActivityFragment extends Fragment {
    String currentUserId; // Current user ID
    Profile profile; // Current user FB Profile
    AccessToken accessToken; // Current user FB AccessToken

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserId = getArguments().getString("currentUserId");
        profile = Profile.getCurrentProfile();
        accessToken = AccessToken.getCurrentAccessToken();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_feed, container, false);
        setHasOptionsMenu(true);
        ((TextView) v.findViewById(R.id.main_username)).setText(profile.getName()); // Display profile name
        ((ProfilePictureView) v.findViewById((R.id.main_userpicture))).setProfileId(profile.getId()); // Display profile pic
        displayUserCraving(); // Display user craving
        displayFeed(); // Display friend feed
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main_feed_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_main) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // MainFeedActivity Fragment instantiater
    public static MainFeedActivityFragment newInstance(String currentUserId) {

        MainFeedActivityFragment f = new MainFeedActivityFragment();
        Bundle b = new Bundle();
        b.putString("currentUserId", currentUserId);

        f.setArguments(b);

        return f;
    }

    // Get and display friends feed
    private void displayFeed() {
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                accessToken,
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray result, GraphResponse graphResponse) {
                        // When request completes, if has results use to populate feed
                        if (result == null) {
                            Log.d("json result", "null");
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
                                    // Toast.makeText(this, "Non-Facebook Network isn't working!", Toast.LENGTH_LONG).show();
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
    }

    // Get current craving for user
    private void displayUserCraving() {
        class CurrentCravingTask extends AsyncTask<String, String, String> {

            @Override
            // Get JSON data on this thread
            protected String doInBackground(String... params) {
                String craving = HttpManager.getData(params[0]);
                Log.d("user current craving",craving);
                return craving;
            }

            @Override
            // Receives and displays profile's current craving and time
            protected void onPostExecute(String result) {
                List<Friend> userInfo = FeedJSONParser.parseFeed(result);
                String craving = userInfo.get(0).getCurrentCraving();
                craving = craving.substring(0,1).toUpperCase() + craving.substring(1);
                ((TextView) getView().findViewById(R.id.main_usercraving)).setText(craving);
                ((TextView) getView().findViewById(R.id.main_usercraving_time)).setText(userInfo.get(0).getCravingTimestamp());
            }
        }

        CurrentCravingTask c = new CurrentCravingTask();
        c.execute("https://dsp-wilbertthelam-53861.cloud.dreamfactory.com/rest/foomoo_db/users?app_name=foomoo&ids=" + currentUserId);
    }

    // Generates the list of friends on the feed
    private void generateFeed(List<Friend> feedList) {
        ProfileListItemAdapter peopleAdapter = new ProfileListItemAdapter(getActivity(), feedList);
        ListView peopleProfileList = (ListView) getView().findViewById(R.id.main_peopleprofile_list);
        peopleProfileList.setAdapter(peopleAdapter);
    }

    // Gets the data from Feed task
    private void requestFeedData(String uri) {
        // Implement AsyncTask to have thread in background to get user
        // feed network from REST endpoint
        class FeedTask extends AsyncTask<String, String, String> {
            @Override
            // Before the AsyncTask is called, contains log info
            protected void onPreExecute() {
                Log.d("PRE-Executing","success");
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
                Log.d("POST-Executing", result);

                List<Friend> friendList = FeedJSONParser.parseFeed(result);
                Log.d("x",friendList.get(0).getCurrentCraving());
                generateFeed(friendList);
            }
        }

        // Execute async task
        FeedTask task = new FeedTask();
        task.execute(uri);
    }

    // Checks to see if network is connected
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
}