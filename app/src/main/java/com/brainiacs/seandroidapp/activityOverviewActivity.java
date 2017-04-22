package com.brainiacs.seandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brainiacs.seandroidapp.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class activityOverviewActivity extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<String>();
    private ArrayList<String> answers = new ArrayList<String>();
    private int numOfQs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Button mResetButton = (Button) findViewById(R.id.reset);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetQuestions();
            }
        });

        Button mAddActivity = (Button) findViewById(R.id.addAssignment);
        mAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAssignment();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this); //Creates new alertBuilder
        builder.setTitle(R.string.NumOfQuestions);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton(R.string.Create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(Integer.parseInt(input.getText().toString()) <= 10 && Integer.parseInt(input.getText().toString()) > 0){
                    numOfQs = Integer.parseInt(input.getText().toString());
                    setViews();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.questionNumCheckToast, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetQuestions();
                dialog.cancel();
            }
        });

        builder.show();
    }

    //Adds all questions
    private void addAssignment(){
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        String selection = String.valueOf(spinner1.getSelectedItem());
        String[] questionIDs = getResources().getStringArray(R.array.questionEntry);
        String[] answerIDs = getResources().getStringArray(R.array.answerEntry);
        //Adds questions and answers to the appropriate arraylists
        for(int i = 0; i < numOfQs; i++){
            EditText qEntry = (EditText) findViewById(getResources().getIdentifier(questionIDs[i], "id", getPackageName()));
            EditText aEntry = (EditText) findViewById(getResources().getIdentifier(answerIDs[i], "id", getPackageName()));
            questions.add(qEntry.getText().toString());
            answers.add(aEntry.getText().toString());
        }
        if(checkQuestionType(selection)){
            //TODO post assignment to DB and add the activity as a button/name the assignment
            resetQuestions();
        }
    }

    //Checks if a question is of the like question type
    private boolean checkQuestionType(String questionType){
        for(int i = 0; i < answers.size(); i++) {
            Pattern pAnswer;
            Pattern pQuestion;
            if(questionType.equals(getString(R.string.addition))){
                pAnswer = Pattern.compile("\\d+");
                pQuestion = Pattern.compile("\\d+\\p{Space}*[+]\\p{Space}*\\d+");
                Matcher mQ = pQuestion.matcher(questions.get(i));
                Matcher m = pAnswer.matcher(answers.get(i));
                if(!m.matches() || !mQ.matches()) {
                    Toast.makeText(getApplicationContext(), R.string.additionFailure + (i+1), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else if(questionType.equals(getString(R.string.subtraction))){
                pAnswer = Pattern.compile("\\d+");
                pQuestion = Pattern.compile("\\d+\\p{Space}*[-]\\p{Space}*\\d+");
                Matcher m = pQuestion.matcher(questions.get(i));
                Matcher mQ = pAnswer.matcher(answers.get(i));
                if(!m.matches() || !mQ.matches()) {
                    Toast.makeText(getApplicationContext(), R.string.subtractionFailure + (i+1), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else{
                pAnswer = Pattern.compile("even|odd");
                pQuestion = Pattern.compile("\\d+");
                Matcher m = pQuestion.matcher(questions.get(i));
                Matcher mQ = pAnswer.matcher(answers.get(i).toLowerCase());
                if(!m.matches() || !mQ.matches()) {
                    Toast.makeText(getApplicationContext(), R.string.oddEvenFailure + (i+1), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    private void setViews(){
        String[] questionViews = getResources().getStringArray(R.array.questionViews);
        for(int i = 0; i < numOfQs; i++){
            RelativeLayout rl1 = (RelativeLayout) findViewById(getResources().getIdentifier(questionViews[i], "id", getPackageName()));
            rl1.setVisibility(View.VISIBLE);
        }
    }

    private void resetQuestions(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }

}
