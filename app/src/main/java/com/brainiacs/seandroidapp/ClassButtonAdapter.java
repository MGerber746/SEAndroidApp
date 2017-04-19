package com.brainiacs.seandroidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.brainiacs.seandroidapp.ClassHomeActivity;
import com.brainiacs.seandroidapp.R;
import com.brainiacs.seandroidapp.TeacherDashboardActivity;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by Matthew on 2/21/17.
 * This class sets up a grid view of buttons
 * on the teacher dashboard.
 * Its necessary for the class buttons
 */

public class ClassButtonAdapter extends BaseAdapter {
    public static final String className = "className";

    //RGB Values need to be individual ints
    private int[] colorArray = {66,149,244,244,66,191,13,219,61,237,22,7,214,7,237};
    private Context mContext;
    private ArrayList<String> buttons = new ArrayList<String>();
    private int i = 0;

    public ClassButtonAdapter(Context c){
        mContext = c;
        initializeClasses();
        buttons.add(c.getString(R.string.Create_New_Class));
    }

    //Returns length of the adapter
    @Override
    public int getCount() {
        return buttons.size();
    }

    //Returns null, is not needed for this class
    @Override
    public Object getItem(int position) {
        return null;
    }

    //Returns position in the
    @Override
    public long getItemId(int position) {
        return position;
    }

    //Sets up buttons on grid.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Button btn;
        if (convertView == null) {
            btn = new Button(mContext);
            btn.setLayoutParams(new GridView.LayoutParams(500, 300));
            btn.setPadding(16, 16, 16, 16);
        }
            else{
                btn = (Button) convertView;
            }

            btn.setText(buttons.get(position));

            //---------------- Apply RGB Values here -------------------
            btn.setBackgroundColor(Color.rgb(colorArray[i], colorArray[i+1], colorArray[i+2]));
            if (i + 2 == colorArray.length - 1) {
                i = 0;
            }
            else {
                i = i + 3;
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((Button) v).getText().toString().equals("Create New Class")){   //Hardcoded string necessary to assertEquals
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(R.string.New_class_name);
                        final EditText input = new EditText(mContext);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton(R.string.Create, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buttons.remove("Create New Class");  //Hardcoded string necessary to point to correct object
                                buttons.add(input.getText().toString());
                                buttons.add(mContext.getString(R.string.Create_New_Class));
                                //TODO POST new class to db
                                Intent intent = new Intent(mContext, ClassHomeActivity.class);
                                intent.putExtra(className, input.getText().toString());
                                mContext.startActivity(intent);
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
                    else {
                        Intent intent = new Intent(mContext, ClassHomeActivity.class);
                        intent.putExtra(className, ((Button) v).getText().toString());
                        mContext.startActivity(intent);
                    }
                }
            });
        return btn;
        }

    private void initializeClasses(){
        for (int i = 0; i < TeacherDashboardActivity.getClassData().size(); ++i) {
            try {
                buttons.add(TeacherDashboardActivity.getClassData().get(i).getString("name"));
            } catch(JSONException e) {}
        }

    }
}
