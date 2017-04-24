package utils.handlers;

import android.content.Context;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brainiacs.seandroidapp.AddToClassActivity;
import com.brainiacs.seandroidapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

import utils.DBTools;


public class TeacherStudentListHandler extends HttpHandler{

    public TeacherStudentListHandler(String apiEndpoint, String success, String failure, Method method,
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
                json = json.getJSONArray("results").getJSONObject(0);
                JSONArray studentList = json.getJSONArray("students");
                LinearLayout nameLayout = AddToClassActivity.nameContainer;
                for(int i = 0; i < studentList.length(); i++){
                    final TextView name = new TextView(context);
                    name.setText(studentList.getJSONObject(i).getJSONObject("user").getString("first_name") + " " +studentList.getJSONObject(i).getJSONObject("user").getString("last_name"));
                    nameLayout.addView(name);
                    final CheckBox checkBox = new CheckBox(context);
                    //checkBox.setId(studentList.getJSONObject(i).getJSONObject("user").);
                }
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
