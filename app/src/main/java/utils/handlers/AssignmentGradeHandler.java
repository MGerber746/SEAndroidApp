package utils.handlers;

import android.content.Context;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.brainiacs.seandroidapp.AssignmentGradesActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by Matthew on 4/29/17.
 */

public class AssignmentGradeHandler extends HttpHandler{
        private String name;

        public AssignmentGradeHandler(String apiEndpoint, String success, String failure, HttpHandler.Method method,
                                          HashMap<String, String> params, Context context, Intent intent, String name) {
            super(apiEndpoint, success, failure, method, params, context, intent);
            this.name = name;
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
                    final AssignmentGradesActivity activity = ((AssignmentGradesActivity) context);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayout nameLayout = AssignmentGradesActivity.studentName;
                            LinearLayout GradeLayout = AssignmentGradesActivity.grade;
                            
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
