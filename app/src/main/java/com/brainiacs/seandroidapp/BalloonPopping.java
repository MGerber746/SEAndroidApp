package com.brainiacs.seandroidapp;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BalloonPopping extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloonpopping);
        playGame();
    }

    protected void playGame() {

        String[]EquationsList = new String[]{"4 + 3 = ?", "5 + 7 = ?", "9 + 8 = ?", "3 + 1 = ?", "7 + 2 = ?",
                                         "5 + 5 = ?", "6 + 2 = ?", "4 + 10 = ?", "9 + 4 = ?", "2 + 4 = ?"};

        final ArrayList <String> Equations = new ArrayList<String>();
        Equations.add("4 + 3 = ?");
        Equations.add("5 + 7 = ?");
        Equations.add("9 + 8 = ?");
        Equations.add("3 + 1 = ?");
        Equations.add("7 + 2 = ?");
        Equations.add("5 + 5 = ?");
        Equations.add("6 + 2 = ?");
        Equations.add("4 + 10 = ?");
        Equations.add("9 + 4 = ?");
        Equations.add("2 + 4 = ?");



        final String [] AnswersArray = new String[] {"7", "12", "17", "4", "9", "10", "8", "14", "13", "6"};

        final ArrayList<String> Answers = new ArrayList<String>();
        Answers.add("7");
        Answers.add("12");
        Answers.add("17");
        Answers.add("4");
        Answers.add("9");
        Answers.add("10");
        Answers.add("8");
        Answers.add("14");
        Answers.add("13");
        Answers.add("6");

        LinearLayout.LayoutParams equationlayout = new LinearLayout.LayoutParams(500, 500);
        LinearLayout llayout = (LinearLayout)findViewById(R.id.balloonpopping);
        final Button EquationButton = new Button(this);
        EquationButton.setText(Equations.get(0));
        llayout.addView(EquationButton);

        for(int i = 0; i < 10; i++)
        {
            LinearLayout.LayoutParams lyt = new LinearLayout.LayoutParams(200, 200);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.balloonpopping);
            Button balloonbutton = new Button(this);
            balloonbutton.setText(AnswersArray[i]);
            balloonbutton.setId(i);
            balloonbutton.setLayoutParams(lyt);
            balloonbutton.setBackgroundResource(R.drawable.greenballoon);
            linearLayout.addView(balloonbutton);
            balloonbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Button button = (Button) v;
                        if (button.getText() == Answers.get(0)) {
                            button.setVisibility(View.INVISIBLE);
                            Answers.remove(0);
                        }
                        else {
                            button.setBackgroundResource(R.drawable.redballoon);
                            Answers.remove(0);
                        }
                    Equations.remove(0);
                    EquationButton.setText(Equations.get(0));
                }

            });
        }

    }


    public void gone(View view) {
        view.setVisibility(View.INVISIBLE);
    }
}