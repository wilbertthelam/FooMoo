package com.cravingscravings.cravings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by linse on 8/17/2015.
 * Adapter to display list of profile's on main page
 */
public class ProfileListItemAdapter extends ArrayAdapter<String> {
    ArrayList<String> people;

    // View lookup cache for person's profile
    private static class ViewHolder {
        ImageView picture;
        TextView name;
        TextView food;
        TextView time;
    }

    // Constructor
    public ProfileListItemAdapter(Context context, ArrayList<String> people) {
        super(context, 0, people);
        this.people = people;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Data string for this position
        String data = people.get(position);

        // Check if existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_profilelistitem_view, parent, false);
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.main_profilelistitem_img);
            viewHolder.name = (TextView) convertView.findViewById(R.id.main_profilelistitem_name);
            viewHolder.food = (TextView) convertView.findViewById(R.id.main_profilelistitem_craving);
            viewHolder.time = (TextView) convertView.findViewById(R.id.main_profilelistitem_time);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the view with data from the data string
        // Data formatted as name,food,time
        String[] dataSplit = data.split(",");
        if (dataSplit.length != 3) {
            System.err.println("Profile list item data parsing error");
        }
        viewHolder.name.setText(dataSplit[0]);
        viewHolder.food.setText(dataSplit[1]);
        viewHolder.time.setText("(" + dataSplit[2] + " ago)");

        return convertView;
    }

}
