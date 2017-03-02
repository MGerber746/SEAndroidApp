package com.brainiacs.seandroidapp.TeacherDashboard;

import android.content.Intent;
import android.view.View;


/**
 * Created by Matthew on 2/21/17.
 * Sets up button with something it needs to do
 */

public class ButtonOnClickListener implements View.OnClickListener {
    private final int position;

    public ButtonOnClickListener(int position) {

        this.position = position;
    }

    @Override
    public void onClick(View view) {
        //classOverview(this);
    }

    private void classOverview() {
        //Intent intent = new Intent(this, ClassHomeActivity.class);
        //startActivity(intent);
    }
}
