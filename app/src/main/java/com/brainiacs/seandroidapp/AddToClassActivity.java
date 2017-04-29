package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;


import utils.handlers.ClassAssignmentListHandler;
import utils.handlers.HttpHandler;
import utils.handlers.TeacherStudentListHandler;

public class AddToClassActivity extends AppCompatActivity {
    public static JSONArray classData;
    public static LinearLayout nameContainer, checkboxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_class);


        Intent oldIntent = getIntent();
        String addType = oldIntent.getStringExtra("Type");
        try {
            classData = new JSONArray(oldIntent.getStringExtra("classData"));
        } catch (JSONException e) {}
        ((TextView) findViewById(R.id.typeOfAddButton)).setText("Add " + addType + " to this class");
        //Retrieves class names from selection Screen

        nameContainer = (LinearLayout) findViewById(R.id.layout1);
        checkboxContainer = (LinearLayout) findViewById(R.id.layout2);
        Button mSubmitButton = (Button) findViewById(R.id.SubmitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitChanges();
            }
        });

        if(addType.equals("students")){
            studentAddToClass();
        }
        if(addType.equals("assignments")){
            assignmentAddToClass();
        }
    }

    //pulls data from teacher and checks any students that are already in the class
    private void studentAddToClass(){
        TeacherStudentListHandler handler = new TeacherStudentListHandler(
                "teachers/", "Fetched classes", "Failed to fetch classes",
                HttpHandler.Method.GET, null, this, null);
        handler.execute((Void) null);
    }

    //Pulls data from teacher assignments and checks any assignments already in the class
    private void assignmentAddToClass(){
        ClassAssignmentListHandler handler = new ClassAssignmentListHandler(
                "teachers/assignments/", "Fetched classes", "Failed to fetch classes",
                HttpHandler.Method.GET, null, this, null);
        handler.execute((Void) null);
    }

    private void submitChanges(){
        //TODO put changes in class students or class assignments
    }
}
