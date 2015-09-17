package com.cravingscravings.cravings;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SearchCravingActivity extends AppCompatActivity {

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_craving);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_craving, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconified(false); // Makes search bar take up whole action bar
        searchView.setQueryHint(null);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        Intent intent = getIntent();
        userID = intent.getStringExtra("CurrentUserID");

        // Listener for submit button in search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // Sets craving on database and brings user back to MainFeedActivity
            public boolean onQueryTextSubmit(String query) {
                Log.d("submit-register", query);

                // Take craving from search bar and send it to database
                updateCraving(query);

                // Return to MainFeedActivity
                Intent i = new Intent(SearchCravingActivity.this, MainFeedActivity.class);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    // Update craving in the database
    private void updateCraving(String craving) {
        String uri = "https://dsp-wilbertthelam-53861.cloud.dreamfactory.com:443/rest/foomoo_db/users/" + userID + "?app_name=foomoo";
        UpdateFieldTask task = new UpdateFieldTask();

        // Set up JSON object as craving parameters for PUT call
        JSONObject j = new JSONObject();
        try {
            j.put("current_craving", craving); // Set current craving
            j.put("craving_timestamp", DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()); // Set current Craving Timestamp
        } catch (JSONException e) {
            e.printStackTrace();
        }

        task.execute(uri, j.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {

        }

        return super.onOptionsItemSelected(item);
    }

    // Implement AsyncTask to have thread in background to get user
    // feed network from REST endpoint
    private class UpdateFieldTask extends AsyncTask<String, String, Integer> {

        @Override
        // Put JSON data for craving update on this thread
        protected Integer doInBackground(String... params) {
            boolean success = HttpManager.updateField(params[0], params[1]);
            Log.d("finished_background","indeed");
            if (success) return 1;
            return 0;
        }

        @Override
        // After the AsyncTask is called, moves feed data to UI thread.
        // Also includes log info
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                Log.d("Craving success", "success");
            }
            else {
                Log.d("Craving success", "failed");
            }
        }
    }
}
