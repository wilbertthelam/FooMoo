package com.cravingscravings.cravings;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.FacebookSdk;

/*
 * Class for login page
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // Initialize Facebook SDK
        setContentView(R.layout.activity_login);

        // Create view pager slides
        int[] slides = {R.drawable.wilbo, R.drawable.wilbo, R.drawable.wilbo};
        LoginPagerAdapter loginPageAdapter = new LoginPagerAdapter(this, slides);
        ((ViewPager) findViewById(R.id.login_viewPager)).setAdapter(loginPageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void sendLogIn(View v) {
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
}
