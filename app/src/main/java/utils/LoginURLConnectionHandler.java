package utils;

import android.content.Context;
import android.content.Intent;

import com.brainiacs.seandroidapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;


public class LoginURLConnectionHandler extends HttpURLConnectionHandler {
    public LoginURLConnectionHandler(String apiEndpoint, String success, String failure, Method method,
                                     HashMap<String, String> params, Context context, Intent intent) {
        super(apiEndpoint, success, failure, method, params, context, intent);
    }

    /**
     * Default method of handling a response.
     * @param conn is the http connection
     * @return a string that is used in the post execute.
     * @throws IOException
     */
    protected String handleResponse(HttpURLConnection conn) throws IOException {
        if(responseCode == HttpURLConnection.HTTP_OK) {
            // Convert the stream to a string
            String line = null;
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
                String token = json.getString(context.getString(R.string.token));
                // Store the token in the database
                DBTools dbTools = new DBTools(context);
                dbTools.createToken(token);
                return success;
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
