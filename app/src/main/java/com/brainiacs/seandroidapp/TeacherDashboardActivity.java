package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.brainiacs.seandroidapp.R;
import com.brainiacs.seandroidapp.LoginSelectionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.ButtonAdapter;
import utils.DBTools;
import utils.HttpURLConnectionHandler;
import utils.JSONTool;
import utils.StudentClassesURLConnectionHandler;

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
        //TODO
        TextView view = (TextView) findViewById(R.id.username);
        view.setText("Teacher_Test");

        TextView view2 = (TextView) findViewById(R.id.schoolname);
        view2.setText("East High School");


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));

        Button mSignInButton = (Button) findViewById(R.id.Logout);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout(){
        DBTools dbTools = new DBTools(this);
        dbTools.deleteTokens();
        dbTools.close();
        Intent intent = new Intent(this, LoginSelectionActivity.class);
        startActivity(intent);
        finish();
    }

    private void initializeData() {
        JSONTool jsonTool = new JSONTool();
        StudentClassesURLConnectionHandler handler = new StudentClassesURLConnectionHandler(
                "teachers/get-classes", "Fetched classes", "Failed to fetch classes",
                HttpURLConnectionHandler.Method.GET, null, this, null, jsonTool);
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
}
