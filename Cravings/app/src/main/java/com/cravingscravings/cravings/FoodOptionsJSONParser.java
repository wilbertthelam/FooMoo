package com.cravingscravings.cravings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilbert Lam on 8/28/2015.
 */
public class FoodOptionsJSONParser {

    public static List<String> parseFeed(String foodOptions) {
        try {
            JSONObject importAr = new JSONObject(foodOptions);
            JSONArray ar = importAr.getJSONArray("food_options");
            List<String> foodOptionsList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                foodOptionsList.add(ar.getString(i));
            }
            return foodOptionsList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
