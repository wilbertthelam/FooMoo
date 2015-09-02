package com.cravingscravings.cravings;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

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
        int[] slides = {R.drawable.loginslide1, R.drawable.loginslide2, R.drawable.loginslide3};
        LoginPagerAdapter loginPageAdapter = new LoginPagerAdapter(this, slides);
        ViewPager loginViewPager = (ViewPager) findViewById(R.id.login_viewPager);
        loginViewPager.setAdapter(loginPageAdapter);
        setupLoginViewPagerIndicator();
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

    // Sets up radio buttons that indicate current page in View Pager
    private void setupLoginViewPagerIndicator() {
        ViewPager loginViewPager = (ViewPager) findViewById(R.id.login_viewPager);
        loginViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                RadioGroup loginRadioGroup = (RadioGroup) findViewById(R.id.login_radiogroup);
                switch (position) {
                    case 0:
                        loginRadioGroup.check(R.id.login_radiobutton1);
                        break;
                    case 1:
                        loginRadioGroup.check(R.id.login_radiobutton2);
                        break;
                    case 2:
                        loginRadioGroup.check(R.id.login_radiobutton3);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels ){}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
