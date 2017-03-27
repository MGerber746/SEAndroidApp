package com.brainiacs.seandroidapp.TeacherDashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

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

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));
    }
}