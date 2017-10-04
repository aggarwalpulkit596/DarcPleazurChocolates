package com.example.pulkit.darcpleazurchocolates;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.pulkit.darcpleazurchocolates.Models.Address;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddressActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    EditText name, state, pincode, mobile, landmark, city, address;
    private static final String TAG = "Payment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setTitle("Address");
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("address");
        bindingview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Address data = dataSnapshot.getValue(Address.class);
                if (data != null) {
                    settingtext(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);

    }

    private void settingtext(Address data) {
        name.setText(data.getFullName());
        mobile.setText(data.getMobile());
        state.setText(data.getState());
        landmark.setText(data.getLandmark());
        pincode.setText(data.getPincode());
        city.setText(data.getCity());
        address.setText(data.getAddressLine1());

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

    public void saveaddress(View view) {
        Address data = new Address(address.getText().toString(),
                city.getText().toString(),
                name.getText().toString(),
                landmark.getText().toString(),
                mobile.getText().toString(),
                state.getText().toString(),
                pincode.getText().toString());
        databaseReference.setValue(data);
        startActivity(new Intent( AddressActivity.this,PaymentActivity.class));
        finish();

    }
}
