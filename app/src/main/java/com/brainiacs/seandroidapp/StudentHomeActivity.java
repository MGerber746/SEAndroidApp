package com.brainiacs.seandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utils.HttpURLConnectionHandler;
import utils.JSONTool;
import utils.StudentClassesURLConnectionHandler;

public class StudentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        JSONTool jsonTool = new JSONTool();
        StudentClassesURLConnectionHandler handler = new StudentClassesURLConnectionHandler(
                getString(R.string.students_get_classes_url), "Fetched classes", "Failed to fetch classes",
                HttpURLConnectionHandler.Method.GET, null, this, null, jsonTool);
        handler.execute((Void) null);
        try {
            // Sleep while we wait for the data
            while (jsonTool.getJsonArray() == null) {
                Thread.sleep(1000);
            }
        } catch(InterruptedException e) {}

        jsonTool.getJsonArray();
    }
}
