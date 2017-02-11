package utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Task will run communications to server through async task
 */
public class HttpURLConnectionHandler extends AsyncTask<Void, Void, String> {
    public enum Method {GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE}
    public static final String ROOT_URL = "https://vast-hollows-88441.herokuapp.com//";
    private String apiEndpoint;
    private Method method;
    private HashMap<String, String> params;
    private int responseCode;
    private Context context;

    /**
     * Sets up the needed information to use the handler
     * @param apiEndpoint represents the endpoint on the server this connection
     *                    needs to connect to
     * @param method represents whether this is a 'GET', 'POST', 'HEAD', 'OPTIONS',
     *               'PUT', 'DELETE', or 'TRACE'
     * @param params required parameters to be put in the url for the given method
     */
    public HttpURLConnectionHandler(String apiEndpoint, Method method,
                                    HashMap<String, String> params, Context context) {
        this.apiEndpoint = apiEndpoint;
        this.method = method;
        this.params = params;
        this.responseCode = 0;
        this.context = context;
    }

    // Starts the communication process with the server
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(ROOT_URL + apiEndpoint);

            // Set the basics of the connection up
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod(method.name());

            // If we have params send them to the server
            if(params != null) {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getParamsString());
                writer.flush();
                writer.close();
                os.close();
            }

            // Get the response code
            responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                StringBuffer sb = new StringBuffer("Success");
                String line = "";
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                while((line=br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            } else {
                return "Response Code: " + responseCode;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    /**
     * Changes the hash map of params into a string representation
     * @return string representation of the params
     */
    private String getParamsString() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()) {
            if(first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
