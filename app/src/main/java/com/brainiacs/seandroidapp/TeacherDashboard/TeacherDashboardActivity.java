package com.brainiacs.seandroidapp.TeacherDashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.brainiacs.seandroidapp.R;
import com.brainiacs.seandroidapp.LoginSelectionActivity;

import utils.DBTools;

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



}
