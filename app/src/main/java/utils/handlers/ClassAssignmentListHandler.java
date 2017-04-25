package utils.handlers;

import android.content.Context;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brainiacs.seandroidapp.AddToClassActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by Matthew on 4/25/17.
 */

public class ClassAssignmentListHandler extends HttpHandler{
    public ClassAssignmentListHandler(String apiEndpoint, String success, String failure, HttpHandler.Method method,
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
                final JSONArray json = new JSONArray(sb.toString());
                final AddToClassActivity activity = ((AddToClassActivity) context);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout nameLayout = AddToClassActivity.nameContainer;
                        LinearLayout checkboxLayout = AddToClassActivity.checkboxContainer;
                        nameLayout.removeAllViewsInLayout();
                        checkboxLayout.removeAllViewsInLayout();
                        for(int i = 0; i < json.length(); i++){
                            final TextView name = new TextView(context);
                            try {
                                name.setText(json.getJSONObject(i).getString("name"));
                                name.setPadding(25, 5, 0, 25);
                                nameLayout.addView(name);
                                final CheckBox checkBox = new CheckBox(context);
                                checkBox.setId(Integer.parseInt(json.getJSONObject(i).getString("id")));
                                checkboxLayout.addView(checkBox);
                            } catch (JSONException e) {
                            }
                        }
                        for(int i = 0; i < checkboxLayout.getChildCount(); i ++){
                            for(int j = 0; j < AddToClassActivity.classData.length(); j ++) {
                                CheckBox checkbox = (CheckBox) checkboxLayout.getChildAt(i);
                                try {
                                    if (Integer.parseInt(AddToClassActivity.classData.getJSONObject(j).getString("id")) == checkbox.getId()) {
                                        checkbox.setChecked(true);
                                    }
                                } catch (JSONException e) {
                                }
                            }
                        }
                    }
                });
                return success;
            } catch (JSONException e) {
                System.err.print(e.getMessage());
                return failure;
            }
        }
        else if(responseCode >= 200 && responseCode < 300) {
            return success; }
        else {
            return failure;
        }
    }
}

