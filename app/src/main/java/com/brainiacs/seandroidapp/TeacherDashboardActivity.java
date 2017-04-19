package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.ButtonAdapter;
import utils.DBTools;
import utils.handlers.HttpHandler;
import utils.JSONTool;
import utils.handlers.ClassesHandler;

/**
 * Created by Matthew on 2/21/17.
 * Sets up grid view with our buttons
 */
public class TeacherDashboardActivity extends AppCompatActivity {
    private static ArrayList<JSONObject> classes_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
        setContentView(R.layout.activity_teacher_dashboard);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));

        Button mLogoutButton = (Button) findViewById(R.id.Logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Button mCreateStudentButton = (Button) findViewById(R.id.CreateStudent);
        mCreateStudentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createStudent();
            }
        });
    }

    private void logout(){
        DBTools dbTools = new DBTools(this);
        dbTools.deleteUsers();
        dbTools.close();
        Intent intent = new Intent(this, LoginSelectionActivity.class);
        startActivity(intent);
        finish();
    }

    private void initializeData() {
        JSONTool jsonTool = new JSONTool();
        ClassesHandler handler = new ClassesHandler(
                getString(R.string.teachers_get_classes_url), "Fetched classes", "Failed to fetch classes",
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
