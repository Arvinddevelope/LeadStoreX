package com.arvind.leadstorex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arvind.leadstorex.R;
import com.arvind.leadstorex.models.Lead;

import java.util.List;

public class LeadAdapter extends ArrayAdapter<Lead> {

    public LeadAdapter(Context context, List<Lead> leads) {
        super(context, 0, leads);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lead lead = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lead, parent, false);

        ((TextView) convertView.findViewById(R.id.leadCustomerName)).setText(lead.getName());
        ((TextView) convertView.findViewById(R.id.leadCustomerNumber)).setText(lead.getNumber());
        ((TextView) convertView.findViewById(R.id.leadNotes)).setText(lead.getNotes());

        return convertView;
    }
}
