package com.example.heathyapp4.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.heathyapp4.R;

import java.util.ArrayList;

public class Adapter<Class> extends ArrayAdapter<Class> {

    private int viewResourceId;
    private LayoutInflater inflater;
    private ArrayList<Class> list;

    public Adapter(Context context, int viewResourceId, ArrayList<Class> list) {
        super(context, viewResourceId, list);
        this.viewResourceId = viewResourceId;
        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder;

        if (arg1 == null || arg1.getTag() == null) {
            holder = new Holder();

            arg1 = inflater.inflate(viewResourceId, null);
            arg1.setTag(holder);



            holder.textView = (TextView) arg1.findViewById(R.id.priceText);
        } else {
            holder = (Holder) arg1.getTag();
        }

        // A,B,C
        if(list.get(arg0)  instanceof CapacityClass){
            holder.textView.setText((int) ((CapacityClass) list.get(arg0)).getPrice());
        } /*else if(list.get(arg0) instanceof B){
            //User B's property to set TextView text
        } else if(list.get(arg0) instanceof C){
            //User C's property to set TextView text
        }*/

        return arg1;
    }

    static class Holder {
        TextView textView;
    }
}
