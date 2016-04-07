package com.ikeres.app.professorearthquake;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;

import android.util.Log;
/**
 * Created by USER on 07/04/2016.
 */
public class ParserJson {

    public void crearURL()throws IOException{

       Integer timeout = 0;
        boolean success;

        URL url = new URL("https://wikipedia.org/");
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-length", "0");
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.connect();


        if(conn.getResponseCode()==201 || conn.getResponseCode()==200)
        {
            success = true;
        }

    }



}
