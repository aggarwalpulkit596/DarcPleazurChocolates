package com.example.pulkit.darcpleazurchocolates;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.pulkit.darcpleazurchocolates.Adapters.ReviewAdapter;
import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;
import com.example.pulkit.darcpleazurchocolates.Models.Comment;
import com.example.pulkit.darcpleazurchocolates.Models.User;
import com.example.pulkit.darcpleazurchocolates.Utils.Constants;
import com.example.pulkit.darcpleazurchocolates.Utils.MySharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChocoDetailActivity extends AppCompatActivity {

    private static final String TAG = "Detail";
    Chocolates mChoco;
    String position;
    @BindView(R.id.image_slider)
    SliderLayout mDemoSlider;
    @BindView(R.id.field_review_text)
    EditText mReviewField;
    @BindView(R.id.button_post_review)
    Button mReviewButton;
    @BindView(R.id.recycler_reviews)
    RecyclerView mReviewsRecycler;
    private ReviewAdapter mAdapter;
    private DatabaseReference mReviewsReference;
    private int cartProductNumber = 0;
    private Gson gson;
    private MySharedPreference sharedPreference;
    private LinearLayout message, line1, line2, line3;
    private EditText txtline1, txtline2, txtline3;
    private TextView title, description, stock, price;
    private boolean sms = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choco_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreference = new MySharedPreference(ChocoDetailActivity.this);
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        if (getIntent().hasExtra(Constants.EXTRA_CHOCO)) {
            mChoco = (Chocolates) getIntent().getSerializableExtra(Constants.EXTRA_CHOCO);
            position = (String) getIntent().getSerializableExtra(Constants.POSITION);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a chocolate Serializable");
        }
        imageslider();
        bindingview();
        mReviewsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-comments").child(position);
        mReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postReview();
            }
        });
        mReviewsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindingview() {

        message = findViewById(R.id.chocolate_message);
        line1 = findViewById(R.id.message_line1);
        line2 = findViewById(R.id.message_line2);
        line3 = findViewById(R.id.message_line3);
        txtline1 = findViewById(R.id.txtline1);
        txtline2 = findViewById(R.id.txtline2);
        txtline3 = findViewById(R.id.txtline3);
        stock = findViewById(R.id.chocolate_stock);
        description = findViewById(R.id.chocolate_description);
        title = findViewById(R.id.chocolate_title);
        price = findViewById(R.id.chocolate_price);


    }

    private void imageslider() {
        for (String name : mChoco.getImages()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.image(name)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(defaultSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ReviewAdapter(this, mReviewsReference);
        mReviewsRecycler.setAdapter(mAdapter);
        title.setText(mChoco.getName());
        price.setText("INR " + mChoco.getPrice() + ".00");
        stock.setText(mChoco.getStock());
        description.setText(mChoco.getDescription());
        if (sms == mChoco.isMessage()) {
            message.setVisibility(View.VISIBLE);
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(9);
            txtline1.setFilters(FilterArray);
            txtline2.setFilters(FilterArray);
            txtline3.setFilters(FilterArray);
            changingview(mChoco.getName());
        }
    }

    private void changingview(String name) {

        switch (name) {
            case "2 Liner":
                line3.setVisibility(View.GONE);
                break;
            case "Wedding Special Chocolate (Small)":
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(6);
                txtline1.setFilters(FilterArray);
                txtline2.setFilters(FilterArray);
                txtline3.setFilters(FilterArray);
            case "Wedding Special Chocolate (Large)":
                InputFilter[] FilterArray8 = new InputFilter[1];
                FilterArray8[0] = new InputFilter.LengthFilter(8);
                txtline1.setFilters(FilterArray8);
                txtline2.setFilters(FilterArray8);
                txtline3.setFilters(FilterArray8);
        }
    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
        mAdapter.cleanupListener();
    }

    private void postReview() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get user information
                        User user = dataSnapshot.getValue(User.class);
                        String name = user.getName();

                        //Create new review object
                        String reviewText = mReviewField.getText().toString();
                        Comment comment = new Comment(uid, name, reviewText);

                        //Push the comment,it will appear in the list
                        mReviewsReference.push().setValue(comment);

                        //Clear the field
                        mReviewField.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void addtocart(View view) {
        String productsFromCart = sharedPreference.retrieveProductFromCart();
        if (productsFromCart.equals("")) {
            List<Chocolates> cartProductList = new ArrayList<>();
            cartProductList.add(mChoco);
            String cartValue = gson.toJson(cartProductList);
            sharedPreference.addProductToTheCart(cartValue);
            cartProductNumber = cartProductList.size();
        } else {
            String productsInCart = sharedPreference.retrieveProductFromCart();
            Chocolates[] storedProducts = gson.fromJson(productsInCart, Chocolates[].class);

            List<Chocolates> allNewProduct = convertObjectArrayToListObject(storedProducts);
            allNewProduct.add(mChoco);
            String addAndStoreNewProduct = gson.toJson(allNewProduct);
            sharedPreference.addProductToTheCart(addAndStoreNewProduct);
            cartProductNumber = allNewProduct.size();
        }
        sharedPreference.addProductCount(cartProductNumber);
        invalidateCart();
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.shopping_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = sharedPreference.retrieveProductCount();
        menuItem.setIcon(buildCounterDrawable(mCount, R.drawable.cart));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
//            Intent checkoutIntent = new Intent(M.this, CheckoutActivity.class);
//            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void invalidateCart() {
        invalidateOptionsMenu();
    }

    private List<Chocolates> convertObjectArrayToListObject(Chocolates[] allProducts) {
        List<Chocolates> mProduct = new ArrayList<>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

}
