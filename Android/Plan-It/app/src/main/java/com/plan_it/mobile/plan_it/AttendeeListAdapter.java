package com.plan_it.mobile.plan_it;

/**
 * Created by Kristian on 21/10/2015.
 */

import android.app.Activity;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AttendeeListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] attendeeNames;
    private final Integer[] imgid;
    private final Integer[] imgStatus;

    public AttendeeListAdapter(Activity context, String[] attendeeNames, Integer[] imgid, Integer[] imgStatus){
        super(context, R.layout.attendee_list, attendeeNames);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.attendeeNames = attendeeNames;
        this.imgid = imgid;
        this.imgStatus = imgStatus;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.attendee_list, null, true);

        TextView txtAttendeeName = (TextView) rowView.findViewById(R.id.tv_attendeeName);
        ImageView imgProfilePic = (ImageView) rowView.findViewById(R.id.iv_profilePic);
        ImageView imgAttendeeStatus = (ImageView) rowView.findViewById(R.id.iv_status);

        txtAttendeeName.setText(attendeeNames[position]);
        imgProfilePic.setImageResource(imgid[position]);
        imgAttendeeStatus.setImageResource(imgStatus[position]);
        return rowView;
    };
}
