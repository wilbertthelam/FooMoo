package com.cravingscravings.cravings;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Wilbert Lam on 8/26/2015.
 * Connects to the network database and pulls records given a REST URL.
 */
public class HttpManager {

    // Get data given REST URL
    public static String getData(String uri) {

        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");

            }
            return sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    // Update a field in a record given REST URL
    public static boolean updateField(String uri, String j) {
        try {
            URL url = new URL(uri);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("PUT");

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(j);
            out.close();
            con.getInputStream();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
