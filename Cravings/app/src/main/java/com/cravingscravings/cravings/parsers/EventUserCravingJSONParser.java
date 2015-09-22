package com.cravingscravings.cravings.parsers;

import com.cravingscravings.cravings.objects.Event;
import com.cravingscravings.cravings.objects.UserCraving;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilbert Lam on 9/19/2015.
 */
public class EventUserCravingJSONParser {
    public static List<UserCraving> parse(String content) {
        try {
            JSONObject importAr = new JSONObject(content);
            if (importAr.getBoolean("Error")) {
                return null;
            }
            JSONArray ar = importAr.getJSONArray("Results");
            List<UserCraving> l = new ArrayList();
            for (int i = 0; i < ar.length(); i++) {
                UserCraving u = new UserCraving();
                JSONObject obj = ar.getJSONObject(i);


                // Get fields out of JSON user craving object
                u.setUserCraving(obj.getString("user_craving"));

                l.add(u);
            }

            return l;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
