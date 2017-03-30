package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brainiacs.seandroidapp.TeacherDashboard.ButtonAdapter;

/**
 * Created by Matthew on 2/21/17.
 * Overview for a class
 */
public class ClassHomeActivity extends AppCompatActivity {
    private String[] assignments = {"Homework", "Classwork", "Pleaswork"};
    private String[] students = {"Gary", "Barry", "Larry", "Harry"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_home);

        Button mStudentLoginButton = (Button) findViewById(R.id.CreateStudent);
        mStudentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStudent();
            }
        });
        Button mActivitiesButton = (Button) findViewById(R.id.assignments_button);
        mActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overviewActivities();
            }
        });

        //Retrieves class names from selection Screen
        Intent oldIntent = getIntent();
        ((TextView)findViewById(R.id.textView)).setText(oldIntent.getStringExtra(ButtonAdapter.className));

        //Sets up student names and grades in a table

/*        TableLayout table = (TableLayout) findViewById(R.id.table);
        TableRow assignRow = new TableRow(this);
        for(int i = 0; i < assignments.length; i++){
            TextView column = new TextView(this);
            column.setText(assignments[i].toString());
            assignRow.addView(column);
        }
        table.addView(assignRow);
        TableRow row = new TableRow(this);
        for(int i = 0; i < students.length; i++){
            TextView column = new TextView(this);
            column.setText(students[i]);
            row.addView(column);

            for(int j = 0; j < assignments.length; j++){
                TextView column1 = new TextView(this);
                column1.setText("1");
                row.addView(column1);

            }
            table.addView(row);

        }*/
    }

    //Redirects to teacher login activity
    private void createStudent() {
        Intent intent = new Intent(this, CreateStudentAccountActivity.class);
        startActivity(intent);
    }

    //Redirects to the activities overview
    private void overviewActivities() {
        Intent intent = new Intent(this, activityOverviewActivity.class);
        startActivity(intent);
    }

}
