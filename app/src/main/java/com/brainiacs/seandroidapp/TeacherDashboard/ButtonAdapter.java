package com.brainiacs.seandroidapp.TeacherDashboard;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;



import com.brainiacs.seandroidapp.TeacherLoginActivity;

import static com.brainiacs.seandroidapp.R.id.gridview;


/**
 * Created by Matthew on 2/21/17.
 * This class sets up a grid view of buttons
 * on the teacher dashboard.
 * Its necessary for the class buttons
 */

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    private String[] buttons = {"Class 1", "Class 2", "Class 3", "Create new Class"};

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
            btn.setLayoutParams(new GridView.LayoutParams(200, 200));
            btn.setPadding(8, 8, 8, 8);
        }
            else{
                btn = (Button) convertView;
            }

            //Sets up button text and makes them buttons
            btn.setText(buttons[position]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((Button) v).getText().toString().equals("Create New Class")){

                    }
                    else {
                        Intent intent = new Intent(mContext, ClassHomeActivity.class);
                        intent.putExtra("className", ((Button) v).getText().toString());
                        mContext.startActivity(intent);
                    }
                }
            });

        return btn;
        }


    //Retrieves reference to number of classes and
    //required amount of buttons
    //TODO
    public void setButtons(){

    }

}
