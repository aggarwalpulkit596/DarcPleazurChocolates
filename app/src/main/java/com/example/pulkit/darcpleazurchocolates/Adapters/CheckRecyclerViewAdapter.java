package com.example.pulkit.darcpleazurchocolates.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckRecyclerViewAdapter extends RecyclerView.Adapter<CheckRecyclerViewAdapter.CheckRecyclerViewHolder> {

    private Context context;
    private List<Chocolates> mProductObject;
    private ChocolatesClickListener mListener;

    public interface ChocolatesClickListener {
        void onItemClick(View view, int position);

        void onRemoveClicked(int position);
    }


    public CheckRecyclerViewAdapter(Context context, List<Chocolates> mProductObject, ChocolatesClickListener mListener) {
        this.context = context;
        this.mProductObject = mProductObject;
        this.mListener = mListener;
    }

    @Override
    public CheckRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.check_layout, parent, false);
        return new CheckRecyclerViewHolder(layoutView, mListener);
    }

    @Override
    public void onBindViewHolder(CheckRecyclerViewHolder holder, int position) {
        //get product quantity
        holder.productName.setText(mProductObject.get(position).getName());
        holder.productPrice.setText(String.valueOf(mProductObject.get(position).getPrice()));
        holder.productName.setText(mProductObject.get(position).getName());
        holder.productName.setText(mProductObject.get(position).getName());
        Picasso.with(context)
                .load(mProductObject.get(position).getImages().get(0))
                .centerCrop()
                .resize(200, 200)
                .into(holder.productimage);
    }

    @Override
    public int getItemCount() {
        return mProductObject.size();
    }

    public class CheckRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView quantity;
        TextView productName;
        TextView productPrice;
        TextView message;
        ImageButton removeButton;
        ImageView productimage;
        CheckRecyclerViewAdapter.ChocolatesClickListener mListener;

        public CheckRecyclerViewHolder(View itemView, ChocolatesClickListener Listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mListener = Listener;
            productimage = itemView.findViewById(R.id.imageView);
            message = itemView.findViewById(R.id.message);
            quantity = itemView.findViewById(R.id.quantity);
            productName = itemView.findViewById(R.id.name);
            productPrice = itemView.findViewById(R.id.price);
            removeButton = itemView.findViewById(R.id.remove);
            removeButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                if(id == R.id.chocolate_layout){
                    mListener.onItemClick(view,position);
                }else if(id == R.id.remove){
                    mListener.onRemoveClicked(position);
                }
            }
        }
    }
}
