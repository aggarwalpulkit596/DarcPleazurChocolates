package com.example.pulkit.darcpleazurchocolates.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pulkit.darcpleazurchocolates.ChocoDetailActivity;
import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.R;
import com.example.pulkit.darcpleazurchocolates.Utils.Constants;
import com.example.pulkit.darcpleazurchocolates.viewHolder.chocolateViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;

/**
 * Created by Pulkit on 9/23/2017.
 */

public class dataFragement extends Fragment {

    RecyclerView mRecyclerview;
    private FirebaseRecyclerAdapter<Chocolates, chocolateViewHolder> mAdapter;
    private LinearLayoutManager mManager;
    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chocolates, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecyclerview = rootView.findViewById(R.id.chocoRecyclerView);
        mManager = new LinearLayoutManager(getActivity());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerview.setLayoutManager(mManager);
        Query chocoQuery = getQuery(mDatabase);

        mAdapter = new FirebaseRecyclerAdapter<Chocolates, chocolateViewHolder>(Chocolates.class, R.layout.chocolate_list_item, chocolateViewHolder.class, chocoQuery) {
            @Override
            protected void populateViewHolder(chocolateViewHolder viewHolder, final Chocolates model, final int position) {
                final DatabaseReference chocoRef = getRef(position);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ChocoDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_CHOCO, model);
                        intent.putExtra(Constants.POSITION, chocoRef.getKey());
                        startActivity(intent);

                    }
                });


                viewHolder.bindChocolate(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("results");
    }
}
