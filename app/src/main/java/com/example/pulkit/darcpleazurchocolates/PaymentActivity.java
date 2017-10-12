package com.example.pulkit.darcpleazurchocolates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.Models.User;
import com.example.pulkit.darcpleazurchocolates.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG ="Payment" ;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private double subTotal;
    TextView address,delivery,paymentMode;
    EditText promocode;
    Button ConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (getIntent().hasExtra(Constants.EXTRA_TOTAL)) {
            subTotal = getIntent().getDoubleExtra(Constants.EXTRA_TOTAL,0);
        } else {
            throw new IllegalArgumentException("Payment activity must recei");
        }
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        bindviews();

    }

    private void bindviews() {

     }

    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener userListerner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                if(user.getAddress()==null){
                    startActivity(new Intent(PaymentActivity.this, AddressActivity.class));
                }
                else{

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: "+databaseError);

            }
        };
        mDatabase.addListenerForSingleValueEvent(userListerner);
    }
}
