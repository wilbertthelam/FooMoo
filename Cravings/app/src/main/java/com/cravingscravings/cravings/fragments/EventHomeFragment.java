package com.cravingscravings.cravings.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cravingscravings.cravings.adapters.CravingVoteListAdapter;
import com.cravingscravings.cravings.adapters.EventListItemAdapter;
import com.cravingscravings.cravings.objects.EventCraving;
import com.cravingscravings.cravings.objects.UserCraving;
import com.cravingscravings.cravings.parsers.EventCravingJSONParser;
import com.cravingscravings.cravings.parsers.EventJSONParser;
import com.cravingscravings.cravings.HttpManager;
import com.cravingscravings.cravings.R;
import com.cravingscravings.cravings.activities.EventActivity;
import com.cravingscravings.cravings.objects.Event;
import com.cravingscravings.cravings.parsers.EventUserCravingJSONParser;

import java.util.List;

/**
 * Fragment containing the Event view for an individual event
 */
public class EventHomeFragment extends Fragment {

    String currentUserId; // Current user ID
    String currentEventId; // Current event ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserId = getArguments().getString("currentUserId");
        currentEventId = getArguments().getString("currentEventId");
        Log.d("EventID3", currentEventId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_home, container, false);
        displayEventInfo();
        displayEventCravings();
        displayUserCravings();

        return v;
    }

    // Display event time, location, and name
    public void displayEventInfo() {
        class EventTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... params) {
                HttpManager http = new HttpManager();
                Log.d("tye1", params[0]);
                String content = http.getDataHttp(params[0]);
                //Log.d("tye",content);
                if (content == null) {
                    Log.d("MYSQL Database", "DOWN");
                    return "FAIL";
                }
                return content;
            }

            @Override
            protected void onPostExecute(String result) {
                Event event;
                if (result == "FAIL") {
                    displayFail();
                } else {
                    event = EventJSONParser.parse(result);
                    generateEventData(event);
                }
            }
        }

        String uri = "http://ec2-52-88-203-84.us-west-2.compute.amazonaws.com:3333/api/events/" + currentEventId;
        EventTask e = new EventTask();
        e.execute(uri);
    }

    // Attach event craving to UI
    private void generateEventInfo(List<EventCraving> event) {
        ((TextView) getView().findViewById(R.id.event_home_event_craving)).setText(event.get(0).getCraving());
    }

    // Attach event data to UI
    private void generateEventData(Event event) {
        ((TextView) getView().findViewById(R.id.event_home_location)).setText(event.getLocation());
        ((TextView) getView().findViewById(R.id.event_home_time)).setText(event.getTime());
        ((EventActivity) getActivity()).setActionBarTitle(event.getEventName());
    }

    public void displayEventCravings() {
        class EventTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... params) {
                HttpManager http = new HttpManager();
                Log.d("tye1", params[0]);
                String content = http.getDataHttp(params[0]);
                //Log.d("tye",content);
                if (content == null) {
                    Log.d("MYSQL Database", "DOWN");
                    return "FAIL";
                }
                return content;
            }

            @Override
            protected void onPostExecute(String result) {
                List<EventCraving> event;
                if (result == "FAIL") {
                    displayFail();
                } else {
                    event = EventCravingJSONParser.parse(result);
                    generateEventCraving(event);
                }
            }
        }

        String uri = "http://ec2-52-88-203-84.us-west-2.compute.amazonaws.com:3333/api/getGroupCravings/" + currentEventId;
        EventTask e = new EventTask();
        e.execute(uri);
    }

    // Attach event craving to UI
    private void generateEventCraving(List<EventCraving> event) {
        EventCraving first = event.get(0);
        ((TextView) getView().findViewById(R.id.event_home_event_craving)).setText(first.getCraving() + " (" + first.getVotes() + ")");
        event.remove(0);
        CravingVoteListAdapter eventAdapter = new CravingVoteListAdapter(getActivity(), event);
        ListView view = (ListView) getView().findViewById(R.id.craving_vote_list_view);
        view.setAdapter(eventAdapter);
    }

    public void displayUserCravings() {
        class EventTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... params) {
                HttpManager http = new HttpManager();
                Log.d("tye1", params[0]);
                String content = http.getDataHttp(params[0]);
                //Log.d("tye",content);
                if (content == null) {
                    Log.d("MYSQL Database", "DOWN");
                    return "FAIL";
                }
                return content;
            }

            @Override
            protected void onPostExecute(String result) {
                List<UserCraving> user;
                if (result == "FAIL") {
                    displayFail();
                } else {
                    user = EventUserCravingJSONParser.parse(result);
                    generateUserCraving(user);
                }
            }
        }

        String uri = "http://ec2-52-88-203-84.us-west-2.compute.amazonaws.com:3333/api/getUserCravingsByEvent/" + currentUserId + "/" + currentEventId;
        EventTask e = new EventTask();
        e.execute(uri);
    }

    // Attach event craving to UI
    private void generateUserCraving(List<UserCraving> user) {
        //String userCraving = user.get(0).getUserCraving();
        //((TextView) getView().findViewById(R.id.event_home_user_craving)).setText(userCraving);
    }

    // Event Fragment instantiater
    public static EventHomeFragment newInstance(String currentUserId, String currentEventId) {

        EventHomeFragment f = new EventHomeFragment();
        Bundle b = new Bundle();
        b.putString("currentUserId", currentUserId);
        b.putString("currentEventId", currentEventId);

        f.setArguments(b);

        return f;
    }

    private void displayFail() {
        Toast.makeText(getActivity().getApplicationContext(), "Uh oh, something's wrong!", Toast.LENGTH_SHORT).show();
    }
}