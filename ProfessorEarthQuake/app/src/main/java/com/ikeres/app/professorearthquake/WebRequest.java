package com.ikeres.app.professorearthquake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WebRequest {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    //Contructor vacío
    public WebRequest() {

    }
    /**
     * Llamada al Web Service
     *
     * @url - url para hacer la petición
     * @requestmethod - método de la petición http
     * @params - parámetros de la petición http
     */

    public String makeWebServiceCall(String url, int requestmethod) {
        return this.makeWebServiceCall(url, requestmethod, null);
    }


    public String makeWebServiceCall(String urladdress, int requestmethod,
                                     HashMap<String, String> params) {
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);


            if (requestmethod == POST) {
                conn.setRequestMethod("POST");
            } else if (requestmethod == GET) {
                conn.setRequestMethod("GET");
                conn.connect();
            }

            if (params != null) {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                writer.write(result.toString());

                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "debug";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
