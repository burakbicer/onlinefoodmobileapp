package com.bilgeadam.mobilefoodapp.order;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.bilgeadam.mobilefoodapp.R;
import com.google.android.material.tabs.TabLayout;

public class OrdersTerminalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_orders);
        setTitle("SİPARİŞLER");
        ViewPager viewPager = findViewById(R.id.soViewpager);

        // Create an adapter that knows which fragment should be shown on each page
        OrdersCategoryAdapter adapter = new OrdersCategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.soTabs);

        tabLayout.setupWithViewPager(viewPager);
    }
}
