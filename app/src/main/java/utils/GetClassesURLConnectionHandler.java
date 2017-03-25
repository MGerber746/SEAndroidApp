package utils;

import android.content.Context;
import android.content.Intent;

import com.brainiacs.seandroidapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthew on 3/9/17.
 */

public class GetClassesURLConnectionHandler extends HttpURLConnectionHandler {
    /**
     * Sets up the needed information to use the handler
     *
     * @param apiEndpoint represents the endpoint on the server this connection
     *                    needs to connect to
     * @param success
     * @param failure
     * @param method      represents whether this is a 'GET', 'POST', 'HEAD', 'OPTIONS',
     *                    'PUT', 'DELETE', or 'TRACE'
     * @param params      required parameters to be put in the url for the given method
     * @param context
     * @param intent
     */
    public GetClassesURLConnectionHandler(String apiEndpoint, String success, String failure, Method method,
                                          HashMap<String, String> params, Context context, Intent intent) {
        super(apiEndpoint, success, failure, method, params, context, intent);
    }

    protected String handleResponse(HttpURLConnection conn) throws IOException {
        if(responseCode == HttpURLConnection.HTTP_OK) {
            // Convert the stream to a string
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            // Create a JSONObject to get our data
            try {
                JSONObject json = new JSONObject(sb.toString());
                String classes = json.getString(context.getString(R.string.class_List));
                return classes;
            } catch (JSONException e) {
                System.err.print(e.getMessage());
                return failure;
            }
        } else if(responseCode >= 200 && responseCode < 300) {
            return success;
        } else {
            return failure;
        }
    }
}
