package com.example.pulkit.darcpleazurchocolates.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pulkit.Chocolates;
import com.example.pulkit.darcpleazurchocolates.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pulkit on 9/8/2017.
 */

public class ChocolateAdapter extends RecyclerView.Adapter<ChocolateAdapter.ChocolateViewHolder> {

    private ArrayList<Chocolates> mChocoList = new ArrayList<>();
    Context mContext;

    public ChocolateAdapter(ArrayList<Chocolates> mChocoList, Context mContext) {
        this.mChocoList = mChocoList;
        this.mContext = mContext;
    }

    @Override
    public ChocolateAdapter.ChocolateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chocolate_list_item,parent,false);
        ChocolateViewHolder chocolateViewHolder =new ChocolateViewHolder(view);
        return chocolateViewHolder;
    }

    @Override
    public void onBindViewHolder(ChocolateViewHolder holder, int position) {
        holder.bindChocolate(mChocoList.get(position));

    }

    @Override
    public int getItemCount() {
        return mChocoList.size();
    }


    public class ChocolateViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        @BindView(R.id.chocolateImageView) ImageView mChocolateImageView;
        @BindView(R.id.chocolateNameTextView) TextView mNameTextView;
        @BindView(R.id.priceTextView) TextView mPriceTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;
        public ChocolateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
//            itemView.setOnClickListener(this);
        }

        public void bindChocolate(Chocolates chocolates){
            Picasso.with(mContext)
                    .load(chocolates.getImages().get(1))
                    .centerCrop()
                    .resize(200,200)
                    .into(mChocolateImageView);
            mNameTextView.setText(chocolates.getName());
            mPriceTextView.setText(chocolates.getPrice());
            mRatingTextView.setText(chocolates.getDescription());
        }
    }
}
