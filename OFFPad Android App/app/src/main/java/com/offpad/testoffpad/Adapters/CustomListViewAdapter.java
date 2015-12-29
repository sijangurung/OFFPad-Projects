package com.offpad.testoffpad.Adapters;

/**
 * Created by djlophu on 14/09/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.offpad.testoffpad.Models.Transactions;
import com.offpad.testoffpad.R;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Transactions> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<Transactions> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtId, txtDestination, txtAmount, txtStatus, txtDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Transactions rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.singlelist, null);
            holder = new ViewHolder();
            holder.txtId = (TextView) convertView.findViewById(R.id.txtId);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.txtDestination = (TextView) convertView.findViewById(R.id.txtDestination);
            holder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //Setting the values to the corresponding fields..
        holder.txtId.setText(rowItem.getId().toString());
        holder.txtDate.setText(rowItem.getDate());
        holder.txtDestination.setText(rowItem.getDestination_Number());
        holder.txtAmount.setText(rowItem.getAmount());
        holder.txtStatus.setText(rowItem.getStatus());
        //This is to set the color for status text...
        if(rowItem.getStatus().equalsIgnoreCase("failed"))
            holder.txtStatus.setTextColor(Color.RED);
        else
            holder.txtStatus.setTextColor(Color.GREEN);

        return convertView;
    }
}