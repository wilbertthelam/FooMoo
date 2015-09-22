package com.cravingscravings.cravings.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cravingscravings.cravings.R;
import com.cravingscravings.cravings.objects.EventCraving;

import java.util.List;

/**
 * Created by Wilbert Lam on 9/18/2015.
 */
public class CravingVoteListAdapter extends ArrayAdapter<EventCraving> {
    List<EventCraving> cravings;

    // View lookup cache for event's profile
    private static class ViewHolder {
        TextView cravings;
        TextView votes;
    }

    public CravingVoteListAdapter(Context context, List<EventCraving> cravings) {
        super(context, 0, cravings);
        this.cravings = cravings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Data object for this position
        EventCraving data = cravings.get(position);

        // Check if the existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.view_craving_vote_list_item, parent, false);
            viewHolder.cravings = (TextView) convertView.findViewById(R.id.craving_vote_list_craving);
            viewHolder.votes = (TextView) convertView.findViewById(R.id.craving_vote_list_votes);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate view with data from EventCraving object
        String cravings = data.getCraving();
        int votes = data.getVotes();

        viewHolder.cravings.setText(cravings);
        viewHolder.votes.setText(votes + "");

        return convertView;
    }

}
