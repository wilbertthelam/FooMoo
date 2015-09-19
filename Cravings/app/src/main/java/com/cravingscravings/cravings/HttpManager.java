package com.cravingscravings.cravings;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    public String getData(String uri) {

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

    public String getData2(String uri) {

        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
    public boolean updateField(String uri, String j) {
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

    // Add a new user
    public String addUser(String uri, String j) {
        try {
            URL url = new URL(uri);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(j);
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            Log.d("AddUserReponse",response.toString());
            in.close();

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


}
