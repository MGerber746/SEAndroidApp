package com.brainiacs.seandroidapp.TeacherDashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TableLayout;

import com.brainiacs.seandroidapp.R;
import com.brainiacs.seandroidapp.TeacherLoginActivity;

/**
 * Created by Matthew on 2/21/17.
 * Overview for a class
 */
public class ClassHomeActivity extends AppCompatActivity {

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
        String className = oldIntent.getStringExtra("className");
        ((TextView)findViewById(R.id.textView)).setText(className);

        //Sets up student names and grades in a table

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
