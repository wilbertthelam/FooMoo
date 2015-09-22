package com.cravingscravings.cravings.parsers;

import com.cravingscravings.cravings.objects.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilbert Lam on 9/19/2015.
 */
public class EventJSONParser {
    public static Event parse(String content) {
        try {
            JSONObject importAr = new JSONObject(content);
            if (importAr.getBoolean("Error")) {
                return null;
            }
            JSONArray ar = importAr.getJSONArray("Results");
            List<Event> eventList = new ArrayList<>();
            Event event = new Event();
            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);


                // Get fields out of JSON event object
                event.setEventId(obj.getString("event_id"));
                event.setEventName(obj.getString("event_name"));
                event.setLocation(obj.getString("location"));
                event.setTime(obj.getString("time"));
                event.setWhenCreated(obj.getString("when_created"));
            }

            return event;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
