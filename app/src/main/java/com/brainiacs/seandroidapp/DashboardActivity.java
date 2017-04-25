package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.DBTools;
import utils.handlers.HttpHandler;
import utils.JSONTool;
import utils.handlers.ClassesHandler;

/**
 * Created by Matthew on 2/21/17.
 * Sets up grid view with our buttons
 */
public class DashboardActivity extends AppCompatActivity {
    private static ArrayList<JSONObject> classes_data;
    private TextView usernameTextView;
    private GridView gridView;
    private Button createStudentButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        initializeData();
        initializeWidgets();
        initializeListeners();
    }

    private void initializeWidgets() {
        DBTools dbTools = new DBTools(this);
        usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(dbTools.getUsername());


        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ClassButtonAdapter(this));


        logoutButton = (Button) findViewById(R.id.Logout);
        logoutButton.setBackgroundColor(getResources().getColor(R.color.Gray));

        createStudentButton = (Button) findViewById(R.id.CreateStudent);
        createStudentButton.setBackgroundColor(getResources().getColor(R.color.Gray));
        if (!dbTools.isTeacher()) {
            createStudentButton.setVisibility(View.GONE);
        }
        dbTools.close();
    }

    private void initializeListeners() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        createStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStudent();
            }
        });
    }

    private void initializeData() {
        JSONTool jsonTool = new JSONTool();
        DBTools dbTools = new DBTools(this);
        ClassesHandler handler;
        if (dbTools.isTeacher()) {
            handler = new ClassesHandler(
                    getString(R.string.teachers_get_classes_url), "Fetched classes", "Failed to fetch classes",
                    HttpHandler.Method.GET, null, this, null, jsonTool);
        } else {
            handler = new ClassesHandler(
                    getString(R.string.students_get_classes_url), "Fetched classes", "Failed to fetch classes",
                    HttpHandler.Method.GET, null, this, null, jsonTool);
        }
        dbTools.close();
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

    private void logout() {
        DBTools dbTools = new DBTools(this);
        dbTools.deleteUsers();
        dbTools.close();
        Intent intent = new Intent(this, LoginSelectionActivity.class);
        finish();
        startActivity(intent);
    }

    //Returns JSONClassData
    public static ArrayList<JSONObject> getClassData(){
        return classes_data;
    }

    //Redirects to teacher login activity
    private void createStudent() {
        Intent intent = new Intent(this, CreateStudentAccountActivity.class);
        startActivity(intent);
    }
}
