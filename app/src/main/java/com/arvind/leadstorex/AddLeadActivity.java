package com.arvind.leadstorex;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.arvind.leadstorex.models.Lead;
import com.arvind.leadstorex.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.*;

public class AddLeadActivity extends AppCompatActivity {

    private EditText nameInput, numberInput, notesInput;
    private Button saveButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);

        nameInput = findViewById(R.id.inputCustomerName);
        numberInput = findViewById(R.id.inputCustomerNumber);
        notesInput = findViewById(R.id.inputCustomerNotes);
        saveButton = findViewById(R.id.buttonSaveLead);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        saveButton.setOnClickListener(v -> saveLead());
    }

    private void saveLead() {
        String name = nameInput.getText().toString().trim();
        String number = numberInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Name and Number are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String leadId = FirebaseUtil.getLeadsRef().child(userId).push().getKey();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Lead lead = new Lead(leadId, name, number, notes, currentDate);
        DatabaseReference ref = FirebaseUtil.getLeadsRef().child(userId).child(leadId);
        ref.setValue(lead).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Lead added", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to add lead", Toast.LENGTH_SHORT).show();
        });
    }
}
