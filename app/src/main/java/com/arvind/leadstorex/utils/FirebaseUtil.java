package com.arvind.leadstorex.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    public static DatabaseReference getLeadsRef() {
        return FirebaseDatabase.getInstance().getReference("Leads");
    }
}
