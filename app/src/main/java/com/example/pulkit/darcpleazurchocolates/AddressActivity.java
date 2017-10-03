package com.example.pulkit.darcpleazurchocolates;

import android.arch.persistence.room.Database;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    EditText name, state, pincode, mobile, landmark, city,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setTitle("Address");
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        bindingview();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void bindingview() {
        name = findViewById(R.id.fullname);
        state = findViewById(R.id.state);
        pincode = findViewById(R.id.pincode);
        mobile = findViewById(R.id.mobile);
        landmark = findViewById(R.id.landmark);
        city = findViewById(R.id.city);
        address = findViewById(R.id.addressline1);

    }
}