package com.cravingscravings.cravings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class EventActivity extends AppCompatActivity {

    String currentUserId; // Storage of logged in user's ID
    String currentEventId; //Storage of the selected event's ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_activity);

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("currentUserId");
        currentEventId = intent.getStringExtra("currentEventId");
        Log.d("EventID2",currentEventId);

        ViewPager pager = (ViewPager) findViewById(R.id.eventViewPager);
        Log.d("LOAD EVENT", "SUCCESSFUL");
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_activitiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Set ActionBar title
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
                case 0: return EventHomeFragment.newInstance(currentUserId, currentEventId);
                //case 1: return EventsListFragment.newInstance(currentUserId);
                default: return EventHomeFragment.newInstance(currentUserId, currentEventId);
            }
        }

        @Override
        // Return number of fragments in Activity
        public int getCount() {
            return 1;
        }
    }
}
