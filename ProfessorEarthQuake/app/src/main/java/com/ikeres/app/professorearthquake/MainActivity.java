package com.ikeres.app.professorearthquake;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {



    // Creamos la URL

    private static String url;

    // Nombres de los nodos del JSON
    private static final String TAG_FEATURES = "features";
    private static final String TAG_PROPERTIES = "properties";
    private static final String TAG_LUGAR = "place";
    private static final String TAG_MAGNITUD = "mag";
    private static final String TAG_HORA = "type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        String nombre = getIntent().getStringExtra("filtro");
switch (nombre){
    case "hora":
    url="http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    break;
    case "dia" :
        url="http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";
    break;
    case "semana":
        url="http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson";
    break;


}

        // Llamando al async task para recoger el JSON
        new GetTerremotos().execute();
    }

    /**
     * Clase asynctask para recoger el JSON haciendo una llamada HTTP
     */
    private class GetTerremotos extends AsyncTask<Void, Void, Void> {

        // Hashmap para el ListView
        ArrayList<HashMap<String, String>> terremotostList;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Instanciar el service handler
            WebRequest webreq = new WebRequest();

            // Llamada a url y obtención de respuesta
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GET);

            Log.d("Response: ", "> " + jsonStr);

            terremotostList = ParseJSON(jsonStr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Quitamos el progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Cargamos el JSON en el ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, terremotostList,
                    R.layout.list_item, new String[]{TAG_LUGAR, TAG_MAGNITUD,
                    TAG_HORA}, new int[]{R.id.lugar,
                    R.id.magnitud, R.id.hora});

            setListAdapter(adapter);
        }

    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {

        if (json != null) {
            try {
                // Hashmap para el ListView
                ArrayList<HashMap<String, String>> hqList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Obtenemos el nodo Array del JSON
                JSONArray terremotos = jsonObj.getJSONArray(TAG_FEATURES);

                // Recorremos todos los terremotos
                for (int i = 0; i < terremotos.length(); i++) {
                    JSONObject c = terremotos.getJSONObject(i);




                    // el nodo "properties" del JSON es un objeto
                    JSONObject properties = c.getJSONObject(TAG_PROPERTIES);
                    String lugar = properties.has(TAG_LUGAR) ? properties.getString(TAG_LUGAR) : null;
                    String magnitud = properties.has(TAG_MAGNITUD) ? properties.getString(TAG_MAGNITUD) : null;
                    String hora = properties.has(TAG_HORA) ? properties.getString(TAG_HORA) : null;


                    // Hashmap temporal para cada terremoto
                    HashMap<String, String> oterremoto = new HashMap<String, String>();

                    // añadimos cada nodo hijo al HashMap
                    oterremoto.put(TAG_LUGAR, lugar);
                    oterremoto.put(TAG_MAGNITUD, magnitud);
                    oterremoto.put(TAG_HORA, hora);


                    // añadimos un terremoto a la lista
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
