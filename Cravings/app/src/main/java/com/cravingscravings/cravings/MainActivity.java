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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/*
 * Class for main page
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Take data from login page fill main page
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        ((TextView) findViewById(R.id.main_username)).setText(username);
        ((ImageView) findViewById((R.id.main_userpicture))).setImageResource(R.drawable.wilbo);

        // Create list view and populate it with data
        if (isOnline()) {
            requestFeedData("https://dsp-wilbertthelam-53861.cloud.dreamfactory.com/rest/foomoo_db/users?app_name=foomoo");
        } else {
            Toast.makeText(this, "Network isn't working!", Toast.LENGTH_LONG).show();
        }
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
            return content;
        }

        @Override
        // After the AsyncTask is called, moves feed data to UI thread.
        // Also includes log info
        protected void onPostExecute(String result) {
            displayText("POST-Executing - " + result);

            List<Friend> friendList = FeedJSONParser.parseFeed(result);
            generateFeed(friendList);
        }
    }
}
