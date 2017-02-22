package com.brainiacs.seandroidapp.TeacherDashboard;

import android.view.View;

/**
 * Created by Matthew on 2/21/17.
 * Sets up button with something it needs to do
 */

public class ButtonOnClickListener implements View.OnClickListener {
    private final int position;

    public ButtonOnClickListener(int position)
    {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        //TODO
        //When clicked do something(this.position)
    }
}
