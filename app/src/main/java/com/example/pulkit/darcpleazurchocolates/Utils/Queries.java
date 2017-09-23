package com.example.pulkit.darcpleazurchocolates.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Pulkit on 9/23/2017.
 */

public class Queries {
    public static Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("results");
    }
}
