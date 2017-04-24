package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.handlers.HttpHandler;
import utils.handlers.TeacherStudentListHandler;

public class AddToClassActivity extends AppCompatActivity {
    private JSONArray classData;
    public static LinearLayout nameContainer, checkboxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_class);

        Intent oldIntent = getIntent();
        String addType = oldIntent.getStringExtra("Type");
        ((TextView) findViewById(R.id.typeOfAddButton)).setText("Add " + addType + "to this class");
        //Retrieves class names from selection Screen

        try {
            classData = new JSONArray(oldIntent.getStringExtra("classData"));
        } catch (JSONException e) {}

        nameContainer = (LinearLayout) findViewById(R.id.layout1);
        checkboxContainer = (LinearLayout) findViewById(R.id.layout2);
        if(addType.equals("students")){
            studentAddToClass();
        }
        //TODO add assignments
    }

    private void studentAddToClass(){
        TeacherStudentListHandler handler = new TeacherStudentListHandler(
                "teachers/", "Fetched classes", "Failed to fetch classes",
                HttpHandler.Method.GET, null, this, null);
        handler.execute((Void) null);
    }

}
