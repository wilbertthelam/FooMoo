package com.cravingscravings.cravings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        ArrayList<String> people = new ArrayList<String>();
        people.add("David,Pizza,9 hours");
        people.add("Jane,Thai,1 hour");
        people.add("Wilburt,Indian,52 minutes");
        people.add("Jody,Mexican,4 days");
        people.add("Frodo,Lembas Bread,10 hours");
        people.add("Luke Skywalker,Sushi,1 minute");
        people.add("Batman,Bat Food,8 days");
        people.add("Rylee,People,14 minutes");
        ProfileListItemAdapter peopleAdapter = new ProfileListItemAdapter(this, people);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
