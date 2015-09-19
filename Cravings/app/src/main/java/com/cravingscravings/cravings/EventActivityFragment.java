package com.cravingscravings.cravings;

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

import java.util.List;

/**
 * Fragment containing the Events view
 */
public class EventActivityFragment extends Fragment {

    String currentUserId; // Current user ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserId = getArguments().getString("currentUserId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_activity, container, false);
        displayHostedEvents(); // Display events
        return v;
    }
    //EventActivity Fragment instantiater
    public static EventActivityFragment newInstance(String currentUserId) {

        EventActivityFragment f = new EventActivityFragment();
        Bundle b = new Bundle();
        b.putString("currentUserId", currentUserId);

        f.setArguments(b);

        return f;
    }

    // Display events that user is in
    public void displayHostedEvents() {
        class EventsTask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {

            }

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
                List<Event> eventList;
                if (result == "FAIL" ) {
                    displayFail();
                } else {
                    eventList = EventJSONParser.parse(result);
                    if (eventList == null) {
                        Log.d("Event status:", "not in any events");
                    }
                    generateEvents(eventList);
                }
            }
        }

        String uri = "http://ec2-52-88-203-84.us-west-2.compute.amazonaws.com:3333/api/eventsByUserId/" + currentUserId;
        EventsTask e = new EventsTask();
        e.execute(uri);
    }

    // Generate events and display
    private void generateEvents(List<Event> eventList) {
        EventListItemAdapter eventAdapter = new EventListItemAdapter(getActivity(), eventList);
        ListView view = (ListView) getView().findViewById(R.id.event_listitem_view);
        view.setAdapter(eventAdapter);
    }

    // Generate screen if events fail to load
    private void displayFail() {
        Toast.makeText(getActivity().getApplicationContext(),"Uh oh, something's wrong!", Toast.LENGTH_SHORT).show();
    }


}