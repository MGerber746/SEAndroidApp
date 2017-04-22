package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.DBTools;
import utils.handlers.HttpHandler;
import utils.JSONTool;
import utils.handlers.ClassesHandler;

public class StudentHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<JSONObject> classes_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        initializeData();
        initializeWidgets();
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        try {
            JSONArray assignments_data = (JSONArray) classes_data.get(button.getId()).get("assignments");
            Intent intent = new Intent(this, StudentAssignmentsActivity.class);
            intent.putExtra("assignments_data", assignments_data.toString());
            startActivity(intent);
        } catch (JSONException e) {}
    }

    private void initializeData() {
        JSONTool jsonTool = new JSONTool();
        ClassesHandler handler = new ClassesHandler(
                getString(R.string.students_get_classes_url), "Fetched classes", "Failed to fetch classes",
                HttpHandler.Method.GET, null, this, null, jsonTool);
        handler.execute((Void) null);
        try {
            // Sleep while we wait for the data
            while (jsonTool.getJsonArray() == null) {
                Thread.sleep(1000);
            }
        } catch(InterruptedException e) {}

        classes_data = new ArrayList<>();
        try {
            for (int i = 0; i < jsonTool.getJsonArray().length(); ++i) {
                classes_data.add(jsonTool.getJsonArray().getJSONObject(i));
            }
        } catch(JSONException e) {}
    }

    private void initializeWidgets() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_student_home);

        // Create buttons for all of the classes
        for (int i = 0; i < classes_data.size(); ++i) {
            Button classButton = new Button(this);
            try {
                classButton.setText(classes_data.get(i).getString("name"));
                classButton.setPadding(32,32,32,32);
                classButton.setId(i);
            } catch(JSONException e) {}
            classButton.setOnClickListener(this);
            linearLayout.addView(classButton);
        }

        Button logoutButton = new Button(this);
        logoutButton.setText("Logout");
        logoutButton.setBackgroundColor(getResources().getColor(R.color.Gray));
        logoutButton.setPadding(32,32,32,32);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        linearLayout.addView(logoutButton);
    }

    private void logout(){
        DBTools dbTools = new DBTools(this);
        dbTools.deleteUsers();
        dbTools.close();
        Intent intent = new Intent(this, LoginSelectionActivity.class);
        startActivity(intent);
        finish();
    }
}
