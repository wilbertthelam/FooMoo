package com.cravingscravings.cravings.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cravingscravings.cravings.HttpManager;
import com.cravingscravings.cravings.R;
import com.cravingscravings.cravings.activities.EventActivity;
import com.cravingscravings.cravings.adapters.EventListItemAdapter;
import com.cravingscravings.cravings.objects.Event;
import com.cravingscravings.cravings.parsers.EventListJSONParser;

import java.util.List;

/**
 * Fragment containing the Events view
 */
public class EventListFragment extends Fragment {

    String currentUserId; // Current user ID
    List<Event> eventList; // List of events

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserId = getArguments().getString("currentUserId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);
        displayHostedEvents(); // Display events
        return v;
    }
    //EventActivity Fragment instantiater
    public static EventListFragment newInstance(String currentUserId) {

        EventListFragment f = new EventListFragment();
        Bundle b = new Bundle();
        b.putString("currentUserId", currentUserId);

        f.setArguments(b);

        return f;
    }

    // Display events that user is in
    public void displayHostedEvents() {
        class EventsTask extends AsyncTask<String, String, String> {

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
                    eventList = EventListJSONParser.parse(result);
                    if (eventList == null) {
                        Log.d("Event status:", "not in any events");
                        displayFail();
                    } else {
                        generateEvents(eventList);
                    }
                }
            }
        }

        String uri = "http://ec2-52-88-203-84.us-west-2.compute.amazonaws.com:3333/api/eventsByUserId/" + currentUserId;
        EventsTask e = new EventsTask();
        e.execute(uri);
    }

    public List<Event> getEventList() {
        return eventList;
    }

    // Generate events and display
    private void generateEvents(List<Event> eventList) {
        this.eventList = eventList;
        EventListItemAdapter eventAdapter = new EventListItemAdapter(getActivity(), eventList);
        ListView view = (ListView) getView().findViewById(R.id.event_listitem_view);
        view.setAdapter(eventAdapter);

        // Click listener to open up Event fragment
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("eventlist click","" + position);
                String eventId = getEventList().get(position).getEventId();
                Log.d("EventID",eventId);

                Intent i = new Intent(getActivity(), EventActivity.class);
                i.putExtra("currentUserId",currentUserId);
                i.putExtra("currentEventId", eventId);
                startActivity(i);
            }
        });
    }

    // Generate screen if events fail to load
    private void displayFail() {
        Toast.makeText(getActivity().getApplicationContext(),"Uh oh, something's wrong!", Toast.LENGTH_SHORT).show();
    }
}