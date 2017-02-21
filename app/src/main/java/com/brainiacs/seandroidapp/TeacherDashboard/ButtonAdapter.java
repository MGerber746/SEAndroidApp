package com.brainiacs.seandroidapp.TeacherDashboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;


/**
 * Created by Matthew on 2/21/17.
 * This class sets up a grid view of buttons
 * on the teacher dashboard.
 * Its necessary for the class buttons
 */

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    private String[] buttons;

    public ButtonAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return buttons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            btn = new Button(mContext);
            btn.setLayoutParams(new GridView.LayoutParams(100, 55));
            btn.setPadding(8, 8, 8, 8);
        }
            else{
                btn = (Button) convertView;
            }

        btn.setOnClickListener(new ButtonOnClickListener(position));
        return btn;
        }


    //Retrieves reference to number of classes and
    //required amount of buttons
    //TODO
    public String[] getButtons(){

        return null;
    }

}
