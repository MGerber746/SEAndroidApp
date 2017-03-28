package com.brainiacs.seandroidapp.TeacherDashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.brainiacs.seandroidapp.R;

/**
 * Created by Matthew on 2/21/17.
 * Sets up grid view with our buttons
 */
public class TeacherDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        TextView view = (TextView) findViewById(R.id.username);
        view.setText("Teacher_Test");

        TextView view2 = (TextView) findViewById(R.id.schoolname);
        view2.setText("East High School");


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));
    }



}
