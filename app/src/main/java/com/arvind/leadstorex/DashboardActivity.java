package com.arvind.leadstorex;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.arvind.leadstorex.models.Lead;
import com.arvind.leadstorex.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardActivity extends AppCompatActivity {

    private TextView todayLeadValue, yesterdayLeadValue, monthLeadValue;
    private Button addLeadButton, viewLeadsButton;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ✅ Set status bar color to purple
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_700));
        }

        // UI Components
        todayLeadValue = findViewById(R.id.todayLeadValue);
        yesterdayLeadValue = findViewById(R.id.yesterdayLeadValue);
        monthLeadValue = findViewById(R.id.monthLeadValue);
        addLeadButton = findViewById(R.id.addLeadButton);
        viewLeadsButton = findViewById(R.id.viewLeadsButton);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Button listeners
        addLeadButton.setOnClickListener(v ->
                startActivity(new Intent(this, AddLeadActivity.class))
        );

        viewLeadsButton.setOnClickListener(v ->
                startActivity(new Intent(this, LeadListActivity.class))
        );

        // Load lead summary
        loadLeadsSummary();
    }

    private void loadLeadsSummary() {
        DatabaseReference leadsRef = FirebaseUtil.getLeadsRef().child(currentUserId);

        leadsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int todayCount = 0;
                int yesterdayCount = 0;
                int thisMonthCount = 0;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                String today = sdf.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_YEAR, -1);
                String yesterday = sdf.format(calendar.getTime());

                calendar = Calendar.getInstance();
                String thisMonth = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(calendar.getTime());

                for (DataSnapshot leadSnapshot : snapshot.getChildren()) {
                    Lead lead = leadSnapshot.getValue(Lead.class);
                    if (lead != null && lead.getDate() != null) {
                        if (lead.getDate().equals(today)) {
                            todayCount++;
                        }
                        if (lead.getDate().equals(yesterday)) {
                            yesterdayCount++;
                        }
                        if (lead.getDate().startsWith(thisMonth)) {
                            thisMonthCount++;
                        }
                    }
                }

                todayLeadValue.setText(String.valueOf(todayCount));
                yesterdayLeadValue.setText(String.valueOf(yesterdayCount));
                monthLeadValue.setText(String.valueOf(thisMonthCount));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
