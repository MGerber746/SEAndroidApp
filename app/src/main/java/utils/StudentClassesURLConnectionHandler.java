package utils;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

public class StudentClassesURLConnectionHandler extends HttpURLConnectionHandler {
    private JSONTool jsonTool;

    public StudentClassesURLConnectionHandler(String apiEndpoint, String success, String failure,
                                              Method method, HashMap<String, String> params,
                                              Context context, Intent intent, JSONTool jsonTool) {
        super(apiEndpoint, success, failure, method, params, context, intent);
        this.jsonTool = jsonTool;
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
                jsonTool.setJsonArray(new JSONArray(sb.toString()));
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

    @Override
    protected void onPostExecute(String result) {
        if(!result.equals(failure) && intent != null) {
            context.startActivity(intent);
        }
    }
}
