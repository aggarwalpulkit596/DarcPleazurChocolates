package com.example.pulkit.darcpleazurchocolates.viewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pulkit.darcpleazurchocolates.R;

public class CheckRecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView quantity, productName, productPrice, removeProduct;

    public CheckRecyclerViewHolder(View itemView) {
        super(itemView);

        quantity = itemView.findViewById(R.id.quantity);
        productName = itemView.findViewById(R.id.product_name);
        productPrice = itemView.findViewById(R.id.product_price);
        removeProduct = itemView.findViewById(R.id.remove_from_cart);
    }
}
