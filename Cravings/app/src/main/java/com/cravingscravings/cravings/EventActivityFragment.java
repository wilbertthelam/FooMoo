package com.cravingscravings.cravings;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventActivityFragment extends Fragment {

    String currentUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_activity, container, false);

        return v;
    }

    public static EventActivityFragment newInstance(String currentUserId) {

        EventActivityFragment f = new EventActivityFragment();
        Bundle b = new Bundle();
        b.putString("currentUserId", currentUserId);

        f.setArguments(b);

        return f;
    }
}