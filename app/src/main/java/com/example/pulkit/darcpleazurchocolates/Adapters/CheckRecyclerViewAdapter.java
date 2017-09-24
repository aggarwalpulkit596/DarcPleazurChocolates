package com.example.pulkit.darcpleazurchocolates.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.R;
import com.example.pulkit.darcpleazurchocolates.Utils.MySharedPreference;
import com.example.pulkit.darcpleazurchocolates.viewHolder.CheckRecyclerViewHolder;
import com.google.gson.Gson;

import java.util.List;

public class CheckRecyclerViewAdapter extends RecyclerView.Adapter<CheckRecyclerViewHolder> {

    private Context context;
    private Gson gson;
    private MySharedPreference sharedPreference;
    private List<Chocolates> mProductObject;


    public CheckRecyclerViewAdapter(Context context, List<Chocolates> mProductObject) {
        this.context = context;
        this.mProductObject = mProductObject;
    }

    @Override
    public CheckRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_layout, parent, false);
        CheckRecyclerViewHolder productHolder = new CheckRecyclerViewHolder(layoutView);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(CheckRecyclerViewHolder holder, int position) {
        //get product quantity
        holder.quantity.setText("1");
        holder.productName.setText(mProductObject.get(position).getName());
        holder.productPrice.setText(String.valueOf(mProductObject.get(position).getPrice()));

        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Do you want to remove product from cart", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductObject.size();
    }
}
