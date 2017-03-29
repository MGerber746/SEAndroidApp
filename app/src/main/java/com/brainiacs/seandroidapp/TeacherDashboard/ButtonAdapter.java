package com.brainiacs.seandroidapp.TeacherDashboard;

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

import com.brainiacs.seandroidapp.R;


/**
 * Created by Matthew on 2/21/17.
 * This class sets up a grid view of buttons
 * on the teacher dashboard.
 * Its necessary for the class buttons
 */

public class ButtonAdapter extends BaseAdapter {
    public static final String className = "className";
    int i = 0;
    //RGB Values need to be individual ints
    int[] colorArray = {66,149,244,244,66,191,13,219,61,237,22,7,214,7,237};
    private Context mContext;
    private String[] buttons = {"1st Grade Period 1", "Period 2", "Second Grade Period 1", "Create New Class"};


    public ButtonAdapter(Context c){

        mContext = c;
    }

    //Returns length of the adapter
    @Override
    public int getCount() {
        return buttons.length;
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
            btn.setPadding(8, 8, 8, 8);
        }
            else{
                btn = (Button) convertView;
            }

            //TODO setButtons();
            //Sets up button text and makes them buttons
            btn.setText(buttons[position]);

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
                    //TODO change hardcoded string to asset
                    if(((Button) v).getText().toString().equals("Create New Class")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(R.string.New_class_name);
                        final EditText input = new EditText(mContext);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton(R.string.Create, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                input.getText().toString();
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


    //Retrieves reference to number of classes and
    //required amount of buttons
    //TODO
    /*public void setButtons(){
        JSONTools jsonData = new JSONTools();
        GetClassesURLConnectionHandler classJSON = new GetClassesURLConnectionHandler("teacher/get-classes", "Data Retrieval Successful",
                "Data Retrieval Failed", HttpURLConnectionHandler.Method.GET, null, mContext, null, jsonData);
        classJSON.execute((Void) null);
        JSONObject json = jsonData.getJSON();
        //try {
            //buttons = json.getString(mContext.getString(R.string.class_List));
        //} catch (JSONException e) {
          //  e.printStackTrace();
        //}

    }*/

}
