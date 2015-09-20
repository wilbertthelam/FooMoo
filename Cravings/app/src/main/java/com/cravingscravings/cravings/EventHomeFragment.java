package com.cravingscravings.cravings;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.d("EventID3",currentEventId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_home, container, false);
        displayEventInfo();




        return v;
    }

    public void displayEventInfo() {
        class EventTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... params) {
                HttpManager http = new HttpManager();
                Log.d("tye1", params[0]);
                String content = http.getData2(params[0]);
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

    // Attach event data to UI
    private void generateEventData(Event event) {
        ((TextView) getView().findViewById(R.id.location)).setText(event.getLocation());
        ((TextView) getView().findViewById(R.id.time)).setText(event.getTime());
        ((TextView) getView().findViewById(R.id.event_home_craving)).setText("Craving");
        ((EventActivity) getActivity()).setActionBarTitle(event.getEventName());
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