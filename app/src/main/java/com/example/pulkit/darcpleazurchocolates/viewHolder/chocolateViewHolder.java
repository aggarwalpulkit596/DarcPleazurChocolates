package com.example.pulkit.darcpleazurchocolates.viewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by Pulkit on 9/8/2017.
 */

public class chocolateViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    public ImageView mChocolateImageView;
    public TextView mNameTextView;
    public TextView mPriceTextView;
    public TextView mStockTextView;
    public RatingBar mStarView;


    public chocolateViewHolder(View itemView) {
        super(itemView);
        mChocolateImageView = itemView.findViewById(R.id.chocolateImageView);
        mNameTextView = itemView.findViewById(R.id.chocolateNameTextView);
        mPriceTextView = itemView.findViewById(R.id.priceTextView);
        mStarView = itemView.findViewById(R.id.star);
        mStockTextView = itemView.findViewById(R.id.stockTextView);
        mContext = itemView.getContext();
    }

    public void bindChocolate(Chocolates chocolates, View.OnClickListener starClickListener) {
        Picasso.with(mContext)
                .load(chocolates.getImages().get(0))
                .centerCrop()
                .resize(200, 200)
                .into(mChocolateImageView);
        Log.i("TAG", "chocolates: " + chocolates.getName() + chocolates.getImages());

        mNameTextView.setText(chocolates.getName());
        mPriceTextView.setText("INR "+chocolates.getPrice()+".00");
        mStarView.setOnClickListener(starClickListener);
        mStockTextView.setText(chocolates.getStock());
    }
}
