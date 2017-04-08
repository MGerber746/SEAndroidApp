package com.brainiacs.seandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.brainiacs.seandroidapp.R;

import java.util.ArrayList;

import utils.Equation;

public class activityOverviewActivity extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Button mActivitiesButton = (Button) findViewById(R.id.createActivityButton);
        mActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewActivity();
            }
        });

        Button mNewQuestionButton = (Button) findViewById(R.id.newQuestion);
        mNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQuestion();
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questions);
    }

    private void createNewActivity(){
        LinearLayout rl1 = (LinearLayout) findViewById(R.id.newActivity);
        rl1.setVisibility(View.VISIBLE);
    }

    private void newQuestion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        String selection = String.valueOf(spinner1.getSelectedItem());
        spinner1.setEnabled(false);
        builder.setTitle("New " + selection + " Question");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(R.string.Create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questions.add(input.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    //TODO when user clicks Create assignment button post to DB
    //TODO reset button that enables the spinner and clears questions
}
