package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/21/17.
 * Overview for a class
 */
public class ClassHomeActivity extends AppCompatActivity {
    private JSONObject classData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_home);

        //Retrieves class names from selection Screen
        Intent oldIntent = getIntent();
        ((TextView)findViewById(R.id.textView)).setText(oldIntent.getStringExtra(ClassButtonAdapter.className));
        ((TextView)findViewById(R.id.textView)).setTextColor(Color.BLACK);
        try {
            classData = new JSONObject(oldIntent.getStringExtra("classData"));
        } catch (JSONException e) {}

        Button showStudentButton = (Button) findViewById(R.id.StudentButton);
        showStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStudents();
            }
        });

        Button showAssignmentButton = (Button) findViewById(R.id.AssignButton);
        showAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAssignments();
            }
        });

        try {
            LinearLayout studentList;
            JSONArray studentNameList = classData.getJSONArray("students");
            for(int i = 0; i < studentNameList.length(); i++){
                TextView studentName = new TextView(this);
                studentName.setPadding(0,20,0,20);
                studentName.setTextColor(Color.BLACK);
                studentName.setGravity(Gravity.CENTER);
                studentName.setText(studentNameList.getJSONObject(i).getJSONObject("user").getString("first_name") + " " + studentNameList.getJSONObject(i).getJSONObject("user").getString("last_name"));
                studentName.setTextSize(20);
                if(i % 3 == 0) {
                    studentList = (LinearLayout) findViewById(R.id.l3);
                    studentList.addView(studentName);
                }
                else if(i % 2 == 0){
                    studentList = (LinearLayout) findViewById(R.id.l2);
                    studentList.addView(studentName);
                }
                else{
                    studentList = (LinearLayout) findViewById(R.id.l1);
                    studentList.addView(studentName);
                }
            }
        } catch (JSONException e) {}

    }

    private void showStudents(){
        Intent intent = new Intent(this, AddToClassActivity.class);
        intent.putExtra("Type", "students");
        try {
            intent.putExtra("classData", classData.getJSONArray("students").toString());
        } catch (JSONException e) {}
        startActivity(intent);
    }

    private void showAssignments(){
        Intent intent = new Intent(this, AddToClassActivity.class);
        intent.putExtra("Type", "assignments");
        try {
            intent.putExtra("classData", classData.getJSONArray("assignments").toString());
        } catch (JSONException e) {}
        startActivity(intent);
    }


}
