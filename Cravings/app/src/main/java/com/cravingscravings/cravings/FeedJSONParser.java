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
 * Created by Wilbert Lam on 8/27/2015.
 */
public class FeedJSONParser {

    // parse the Feed JSON object for Feed info
    public static List<Friend> parseFeed(String content) {

        try {
            JSONObject importAr = new JSONObject(content);
            JSONArray ar = importAr.getJSONArray("record");
            List<Friend> friendList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Friend friend = new Friend();

                // get fields out of JSON feed object
                friend.setUserId(obj.getInt("user_id"));
                friend.setFname(obj.getString("fname"));
                friend.setLname(obj.getString("lname"));
                friend.setCurrentCraving(obj.getString("current_craving"));
                friend.setFav1(obj.getString("fav_1"));
                friend.setFav2(obj.getString("fav_2"));
                friend.setFav3(obj.getString("fav_3"));

                String craving_timestamp = obj.getString("craving_timestamp");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(craving_timestamp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long timeInMilliseconds = date.getTime();
                Log.d("tag", "" + timeInMilliseconds);

                long currentTime = System.currentTimeMillis();
                Log.d("currenttime", "" + currentTime);

                String relativeTime = DateUtils.getRelativeTimeSpanString(timeInMilliseconds, currentTime, 0L, DateUtils.FORMAT_ABBREV_RELATIVE).toString();

                friend.setCravingTimestamp(relativeTime);

                friendList.add(friend);

            }

            return friendList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
