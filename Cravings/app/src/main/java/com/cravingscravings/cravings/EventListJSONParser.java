package com.cravingscravings.cravings;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JSON Parser to parse through event objects
 * Created by Wilbert Lam on 9/18/2015.
 */
public class EventListJSONParser {

    // parse the Feed JSON object for Feed info, returns list of Event objects
    // usable by the main UI.
    public static List<Event> parse(String content) {
        try {
            JSONObject importAr = new JSONObject(content);
            if (importAr.getBoolean("Error")) {
                return null;
            }
            JSONArray ar = importAr.getJSONArray("Results");
            List<Event> eventList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Event event = new Event();

                // Get fields out of JSON event object
                event.setUserId(obj.getString("user_id"));
                event.setEventId(obj.getString("event_id"));
                event.setEventName(obj.getString("event_name"));
                event.setLocation(obj.getString("location"));
                event.setTime(obj.getString("time"));
                event.setWhenCreated(obj.getString("when_created"));

                eventList.add(event);

            }

            return eventList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
