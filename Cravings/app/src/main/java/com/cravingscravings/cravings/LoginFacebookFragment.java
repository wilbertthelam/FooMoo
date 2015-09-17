package com.cravingscravings.cravings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by linse on 9/3/2015.
 * Handles the callback event from Facebook login
 */
public class LoginFacebookFragment extends Fragment {

    private CallbackManager mCallbackManager;
    private FacebookCallback<LoginResult> callBack;
    private ProfileTracker mProfileTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        // Logs user in when profile changes (Logs in or logged in)
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                // Make sure that user is logging in and not logging out
                if (profile == null && profile2 != null) {
                    mProfileTracker.stopTracking();
                    Intent intent = new Intent(getActivity(), MainFeedActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
            }
        };
        mProfileTracker.startTracking();

        // Handles result of attempted Facebook login
        callBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("blarg", "Logged In");
            }

            @Override
            public void onCancel() {
                Log.d("blarg", "Cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("blarg", "Error");
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_facebook_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, callBack);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
