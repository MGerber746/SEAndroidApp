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

import utils.HttpURLConnectionHandler;
import utils.JSONTool;
import utils.StudentClassesURLConnectionHandler;

public class StudentAssignmentsActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<JSONObject> assignments_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignments);

        initializeData();
        initializeWidgets();
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        try {
            JSONArray questions_data = (JSONArray) assignments_data.get(button.getId()).get("questions");
            Intent intent = new Intent(this, DuckGameActivity.class);
            intent.putExtra("questions_data", questions_data.toString());
            startActivity(intent);
        } catch (JSONException e) {}
    }

    private void initializeData() {
        assignments_data = new ArrayList<>();
        try {
            JSONArray temp_data = new JSONArray(this.getIntent().getExtras().getString("assignments_data"));
            for (int i = 0; i < temp_data.length(); ++i) {
                assignments_data.add(temp_data.getJSONObject(i));
            }
        } catch(JSONException e) {}
    }

    private void initializeWidgets() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_student_assignments);

        // Create buttons for all of the assignments
        for (int i = 0; i < assignments_data.size(); ++i) {
            Button classButton = new Button(this);
            try {
                classButton.setText(assignments_data.get(i).getString("name"));
                classButton.setTextColor(getResources().getColor(R.color.White));
                classButton.setBackgroundColor(getResources().getColor(R.color.Gray));
                classButton.setId(i);
            } catch(JSONException e) {}
            classButton.setOnClickListener(this);
            linearLayout.addView(classButton);
        }
    }
}
