package com.cravingscravings.cravings.parsers;

import com.cravingscravings.cravings.objects.Event;
import com.cravingscravings.cravings.objects.EventCraving;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilbert Lam on 9/22/2015.
 */
public class EventCravingJSONParser {
    public static List<EventCraving> parse(String content) {
        try {
            JSONObject importAr = new JSONObject(content);
            if (importAr.getBoolean("Error")) {
                return null;
            }
            JSONArray ar = importAr.getJSONArray("Results");
            List<EventCraving> eventList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                EventCraving event = new EventCraving();
                JSONObject obj = ar.getJSONObject(i);


                // Get fields out of JSON event object
                event.setCraving(obj.getString("craving"));
                event.setVotes(obj.getInt("votes"));

                eventList.add(event);
            }

            return eventList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
