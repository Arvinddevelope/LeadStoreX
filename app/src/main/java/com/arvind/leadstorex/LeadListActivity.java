package com.arvind.leadstorex;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.arvind.leadstorex.R;
import com.arvind.leadstorex.models.Lead;
import com.arvind.leadstorex.adapters.LeadAdapter;
import com.arvind.leadstorex.utils.FirebaseUtil;

import java.util.ArrayList;

public class LeadListActivity extends AppCompatActivity {

    private ListView leadListView;
    private ArrayList<Lead> leadList;
    private LeadAdapter adapter;
    private DatabaseReference leadsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_list);

        leadListView = findViewById(R.id.leadListView);
        leadList = new ArrayList<>();
        adapter = new LeadAdapter(this, leadList);
        leadListView.setAdapter(adapter);

        leadsRef = FirebaseUtil.getLeadsRef();
        String uid = FirebaseAuth.getInstance().getUid();

        leadsRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                leadList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Lead lead = snap.getValue(Lead.class);
                    if (lead != null) leadList.add(lead);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
