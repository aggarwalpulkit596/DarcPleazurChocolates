package com.example.pulkit.darcpleazurchocolates;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.viewHolder.chocolateViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public static final String EXTRA_CHOCO = "choco";
    public static final String POSITION = "";

    @BindView(R.id.chocoRecyclerView) RecyclerView mRecyclerview;
    private FirebaseRecyclerAdapter<Chocolates,chocolateViewHolder> mAdapter;
    private LinearLayoutManager mManager;
    private GridLayoutManager nManager;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Main2Activity.this, LogInActivity.class));
                    finish();
                }
            }
        };
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecyclerview.setHasFixedSize(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout) {

            mAuth.signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mManager = new LinearLayoutManager(this);
        nManager = new GridLayoutManager(this,2);
        mRecyclerview.setLayoutManager(mManager);
        Query chocoQuery = getQuery(mDatabase);
        Log.i("TAG", "onStart: "+chocoQuery);

        mAdapter = new FirebaseRecyclerAdapter<Chocolates, chocolateViewHolder>(Chocolates.class,R.layout.chocolate_list_item,chocolateViewHolder.class,chocoQuery) {
            @Override
            protected void populateViewHolder(chocolateViewHolder viewHolder, final Chocolates model, final int position) {
                final DatabaseReference chocoRef = getRef(position);
                Log.i("TAG", "onStart: "+chocoRef);
                Log.i("TAG", "onStart: "+model);


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),ChocoDetailActivity.class);
                        intent.putExtra(Main2Activity.EXTRA_CHOCO,model);
                        intent.putExtra(Main2Activity.POSITION,chocoRef.getKey());
                        startActivity(intent);

                    }
                });

                if(model.stars.containsKey(getUid())){
                    viewHolder.mStarView.setImageResource(R.drawable.ic_toggle_star_24);
                }else {
                    viewHolder.mStarView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }
                
                viewHolder.bindChocolate(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        
                    }
                });
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("results");
    }
}
