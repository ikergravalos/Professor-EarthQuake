package com.ikeres.app.professorearthquake;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    // URL to get contacts JSON
    private static String url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";

    // JSON Node names
    private static final String TAG_FEATURES = "features";
    private static final String TAG_PROPERTIES = "properties";
    private static final String TAG_LUGAR = "lugar";
    private static final String TAG_MAGNITUD = "magnitud";
    private static final String TAG_HORA = "hora";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Calling async task to get json
        new GetTerremotos().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTerremotos extends AsyncTask<Void, Void, Void> {

        // Hashmap for ListView
        ArrayList<HashMap<String, String>> terremotostList;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GET);

            Log.d("Response: ", "> " + jsonStr);

            terremotostList = ParseJSON(jsonStr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, terremotostList,
                    R.layout.list_item, new String[]{TAG_LUGAR, TAG_MAGNITUD,
                    TAG_HORA}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});

            setListAdapter(adapter);
        }

    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {

        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> hqList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray terremotos = jsonObj.getJSONArray(TAG_FEATURES);

                // looping through All HeartQuakes
                for (int i = 0; i < terremotos.length(); i++) {
                    JSONObject c = terremotos.getJSONObject(i);




                    // Properties node is JSON Object
                    JSONObject properties = c.getJSONObject(TAG_PROPERTIES);
                    String lugar = properties.has(TAG_LUGAR) ? properties.getString(TAG_LUGAR) : null;
                    String magnitud = properties.has(TAG_MAGNITUD) ? properties.getString(TAG_MAGNITUD) : null;
                    String hora = properties.has(TAG_HORA) ? properties.getString(TAG_HORA) : null;


                    // tmp hashmap for single hq
                    HashMap<String, String> oterremoto = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    oterremoto.put(TAG_LUGAR, lugar);
                    oterremoto.put(TAG_MAGNITUD, magnitud);
                    oterremoto.put(TAG_HORA, hora);


                    // adding student to hq list
                    hqList.add(oterremoto);
                }
                return hqList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

}
