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
 * Parses the JSON file into a List of type Friend to be accessed by the UI.
 */
public class UserExistsJSONParser {

    // parse the UserExists JSON object to if it exists, returns boolean
    // usable by the main UI.
    public static boolean parseFeed(String content) {

        try {
            JSONObject importAr = new JSONObject(content);
            JSONArray ar = importAr.getJSONArray("record");
            if (ar == null)
                return false;
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
