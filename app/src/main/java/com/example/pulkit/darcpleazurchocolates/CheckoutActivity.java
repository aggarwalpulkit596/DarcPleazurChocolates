package com.example.pulkit.darcpleazurchocolates;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pulkit.darcpleazurchocolates.Adapters.CheckRecyclerViewAdapter;
import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.Utils.MySharedPreference;
import com.example.pulkit.darcpleazurchocolates.Utils.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

    private RecyclerView checkRecyclerView;

    private TextView subTotal;

    private double mSubTotal = 0;

    List<Chocolates> productList = new ArrayList<>();

    CheckRecyclerViewAdapter mAdapter;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Over Cart");

        subTotal = findViewById(R.id.sub_total);

        checkRecyclerView = findViewById(R.id.checkout_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckoutActivity.this);
        checkRecyclerView.setLayoutManager(linearLayoutManager);
        checkRecyclerView.setHasFixedSize(true);
        checkRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(CheckoutActivity.this));

        // get content of cart

        ChocolateDatabse cb = ChocolateDatabse.getInstance(this);
        final ChocolateDao dao = cb.chocolateDao();
        new AsyncTask<Void, Void, List<Chocolates>>() {
            @Override
            protected List<Chocolates> doInBackground(Void... voids) {
                return dao.getAllChocolates();
            }

            @Override
            protected void onPostExecute(List<Chocolates> chocolates) {
                productList.clear();
                productList.add((Chocolates) chocolates);
                mAdapter.notifyDataSetChanged();

            }
        }.execute();


        mAdapter = new CheckRecyclerViewAdapter(CheckoutActivity.this, productList);
        checkRecyclerView.setAdapter(mAdapter);

        mSubTotal = getTotalPrice(productList);
        subTotal.setText("Subtotal excluding tax and shipping: " + String.valueOf(mSubTotal));

        Button shoppingButton = findViewById(R.id.shopping);
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingIntent = new Intent(CheckoutActivity.this, MainActivity.class);
                startActivity(shoppingIntent);
            }
        });
//
//        Button checkButton = (Button)findViewById(R.id.checkout);
//        assert checkButton != null;
//        checkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent paymentIntent = new Intent(CheckoutActivity.this, PayPalCheckoutActivity.class);
//                paymentIntent.putExtra("TOTAL_PRICE", mSubTotal);
//                startActivity(paymentIntent);
//            }
//        });
    }


    private double getTotalPrice(List<Chocolates> mProducts){
        double totalCost = 0;
        for(int i = 0; i < mProducts.size(); i++){
            Chocolates pObject = mProducts.get(i);
            totalCost = totalCost + pObject.getPrice();
        }
        return totalCost;
    }
}
