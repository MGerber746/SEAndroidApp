package com.brainiacs.seandroidapp;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import utils.Equation;
import utils.Point;


public class BalloonPoppingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView equationTextView;
    private RelativeLayout relativeLayout;
    private ArrayList<Equation> equations;
    private ArrayList<Point> points;
    private Equation currentEquation;
    private int correctAnswers;
    private int incorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloonpopping);
        initializeClassVariables();
        initializeWidgets();
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        // Check our current equation compared to the answer
        if (button.getId() == Integer.parseInt(currentEquation.getAnswer())) {
            // They got the right answer so set the button invisible and increment
            // correct answers
            button.setVisibility(View.INVISIBLE);
            ++correctAnswers;
        } else {
            // They got the wrong answer so turn the balloon that has the right
            // answer red and increment incorrect answers
            button = (Button) relativeLayout.findViewById(Integer.parseInt(currentEquation.getAnswer()));
            button.setBackgroundResource(R.drawable.redballoon);
            button.setClickable(false);
            ++incorrectAnswers;
        }
        // Set up our next equation or go to next activity
        if (!equations.isEmpty()) {
            currentEquation = getRandomEquation();
            equationTextView.setText(currentEquation.getEquation());
        } else {
            // Create alert with score
            final Intent intent = new Intent(this, this.getClass());
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Score");
            alertDialogBuilder.setMessage("Answers Correct: " + correctAnswers + "\nAnswers Incorrect: " + incorrectAnswers);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(intent);
                }
            });

            // create the box
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }

    private void initializeClassVariables() {
        // Initialize points to an empty array list
        points = new ArrayList<>();

        // Get equations setup
        equations = new ArrayList<>();
        equations.add(new Equation("4 + 3 = ?", "7"));
        equations.add(new Equation("5 + 7 = ?", "12"));
        equations.add(new Equation("9 + 8 = ?", "17"));
        equations.add(new Equation("3 + 1 = ?", "4"));
        equations.add(new Equation("7 + 2 = ?", "9"));
        equations.add(new Equation("5 + 5 = ?", "10"));
        equations.add(new Equation("6 + 2 = ?", "8"));
        equations.add(new Equation("4 + 10 = ?", "14"));
        equations.add(new Equation("9 + 4 = ?", "13"));
        equations.add(new Equation("2 + 4 = ?", "6"));

        // Start correct and incorrect answers at zero
        correctAnswers = 0;
        incorrectAnswers = 0;
    }

    private void initializeWidgets() {
        // Get out linear layout and equation text view
        relativeLayout = (RelativeLayout) findViewById(R.id.balloonpopping);
        equationTextView = (TextView) findViewById(R.id.equation);
        equationTextView.setTextSize(getResources().getDimension(R.dimen.textsize));

        // Create buttons for all of the answers
        for (Equation equation : equations) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(350, 350);
            setRandomPlacement(layoutParams);
            Button balloonButton = new Button(this);
            balloonButton.setTextSize(getResources().getDimension(R.dimen.textsize));
            balloonButton.setText(equation.getAnswer());
            balloonButton.setId(Integer.parseInt(equation.getAnswer()));
            balloonButton.setLayoutParams(layoutParams);
            balloonButton.setBackgroundResource(R.drawable.greenballoon);
            balloonButton.setOnClickListener(this);
            relativeLayout.addView(balloonButton);
        }

        // Set the first equation
        currentEquation = getRandomEquation();
        equationTextView.setText(currentEquation.getEquation());
    }

    private Equation getRandomEquation() {
        int position = new Random().nextInt(equations.size());
        return equations.remove(position);
    }

    private void setRandomPlacement(RelativeLayout.LayoutParams layoutParams) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Random r = new Random();
        Point point = new Point();
        do {
            point.setX(r.nextInt(displayMetrics.widthPixels - 350));
            point.setY(r.nextInt(displayMetrics.heightPixels - 500));
        } while (isOverlap(point));
        points.add(point);
        layoutParams.leftMargin = point.getX();
        layoutParams.topMargin = point.getY();
    }

    private boolean isOverlap(Point point) {
        for (Point existingPoint : points) {
            if (((point.getX() >= (existingPoint.getX() - 350)) && (point.getX() <= (existingPoint.getX() + 350))) &&
                    ((point.getY() >= (existingPoint.getY() - 350)) && (point.getY() <= (existingPoint.getY() + 350)))) {
                return true;
            }
        }
        return false;
    }
}