package com.example.pulkit.darcpleazurchocolates;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pulkit.darcpleazurchocolates.Adapters.CheckRecyclerViewAdapter;
import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.Utils.Constants;
import com.example.pulkit.darcpleazurchocolates.Utils.MySharedPreference;
import com.example.pulkit.darcpleazurchocolates.Utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

    private RecyclerView checkRecyclerView;

    private TextView subTotal;

    private double mSubTotal = 0;

    List<Chocolates> productList = new ArrayList<>();

    CheckRecyclerViewAdapter mAdapter;

    private MySharedPreference sharedPreference;

    private int cartProductNumber = 0;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Over Cart");

        subTotal = findViewById(R.id.sub_total);
        sharedPreference = new MySharedPreference(CheckoutActivity.this);

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
                productList.addAll(chocolates);
                mAdapter.notifyDataSetChanged();
                mSubTotal = getTotalPrice(productList);
                subTotal.setText("Subtotal excluding tax and shipping: " + String.valueOf(mSubTotal));
            }
        }.execute();


        mAdapter = new CheckRecyclerViewAdapter(CheckoutActivity.this, productList, new CheckRecyclerViewAdapter.ChocolatesClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Chocolates model = productList.get(position);
//                Intent intent = new Intent(CheckoutActivity.this, ChocoDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_CHOCO, model);
//                intent.putExtra(Constants.POSITION, position);
//                startActivity(intent);
            }

            @Override
            public void onRemoveClicked(final int position) {
                new AsyncTask<Void, Void, List<Chocolates>>() {
                    @Override
                    protected List<Chocolates> doInBackground(Void... voids) {
                        dao.deletefromcart(productList.get(position));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(List<Chocolates> chocolates) {
                        productList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        mSubTotal = getTotalPrice(productList);
                        subTotal.setText("Subtotal excluding tax and shipping: " + String.valueOf(mSubTotal));
//                        cartProductNumber = productList.size();
//                        sharedPreference.addProductCount(cartProductNumber);
                    }
                }.execute();
            }
        });
        checkRecyclerView.setAdapter(mAdapter);


        Button shoppingButton = findViewById(R.id.shopping);
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingIntent = new Intent(CheckoutActivity.this, MainActivity.class);
                startActivity(shoppingIntent);
            }
        });

        Button checkButton = (Button)findViewById(R.id.checkout);
        assert checkButton != null;
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                paymentIntent.putExtra(Constants.EXTRA_TOTAL, mSubTotal);
                startActivity(paymentIntent);
            }
        });
    }


    private double getTotalPrice(List<Chocolates> mProducts) {

        double totalCost = 0;
        for (int i = 0; i < mProducts.size(); i++) {
            Chocolates pObject = mProducts.get(i);
            totalCost = totalCost + pObject.getPrice();
            Log.i(TAG, "getTotalPrice: " + pObject.getPrice());
        }
        return totalCost;
    }
}
