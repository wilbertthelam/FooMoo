package com.cravingscravings.cravings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.List;

/**
 * Created by linsen peepee wu on 8/17/2015.
 * Adapter to display list of profile's on main page
 */
public class ProfileListItemAdapter extends ArrayAdapter<Friend> {
    List<Friend> people;

    // View lookup cache for person's profile
    private static class ViewHolder {
        ProfilePictureView picture;
        TextView name;
        TextView food;
        TextView time;
    }

    // Constructor
    public ProfileListItemAdapter(Context context, List<Friend> people) {
        super(context, 0, people);
        this.people = people;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Data object for this position
        Friend data = people.get(position);

        // Check if existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_profilelistitem_view, parent, false);
            viewHolder.picture = (ProfilePictureView) convertView.findViewById(R.id.main_profilelistitem_img);
            viewHolder.name = (TextView) convertView.findViewById(R.id.main_profilelistitem_name);
            viewHolder.food = (TextView) convertView.findViewById(R.id.main_profilelistitem_craving);
            viewHolder.time = (TextView) convertView.findViewById(R.id.main_profilelistitem_time);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the view with data from the Friend object
        String userId = data.getUserId();
        String fname = data.getFname();
        String lname = data.getLname();
        String currentCraving = data.getCurrentCraving();
        String fav1 = data.getFav1();
        String fav2 = data.getFav2();
        String fav3 = data.getFav3();
        String craving_timestamp = data.getCravingTimestamp();

        // Sets view for each individual
        viewHolder.picture.setProfileId(userId);
        viewHolder.name.setText(fname + " "  + lname + " (" + userId + ")");
        viewHolder.food.setText(currentCraving);
        viewHolder.time.setText("(" + craving_timestamp + ")");

        return convertView;
    }

}
