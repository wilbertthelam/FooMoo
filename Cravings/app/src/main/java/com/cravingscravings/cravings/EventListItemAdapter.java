package com.cravingscravings.cravings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Wilbert Lam on 9/18/2015.
 */
public class EventListItemAdapter extends ArrayAdapter<Event> {
    List<Event> events;

    // View lookup cache for event's profile
    private static class ViewHolder {
        TextView eventName;
        TextView eventLocation;
        TextView eventTime;
    }

    public EventListItemAdapter(Context context, List<Event> events) {
        super(context, 0, events);
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Data object for this position
        Event data = events.get(position);

        // Check if the existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_listitem_view, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.event_listitem_view_name);
            viewHolder.eventLocation = (TextView) convertView.findViewById(R.id.event_listitem_view_location);
            viewHolder.eventTime = (TextView) convertView.findViewById(R.id.event_listitem_view_time);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate view with data from Event object
        String userId = data.getUserId();
        String eventId = data.getEventId();
        String eventName = data.getEventName();
        String time = data.getTime();
        String whenCreated = data.getWhenCreated();
        String location = data.getLocation();

        viewHolder.eventTime.setText(time);
        viewHolder.eventName.setText(eventName + " (" + eventId + ")");
        viewHolder.eventLocation.setText(location);

        return convertView;
    }

}
