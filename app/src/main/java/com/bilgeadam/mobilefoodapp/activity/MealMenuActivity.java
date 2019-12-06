package com.bilgeadam.mobilefoodapp.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bilgeadam.mobilefoodapp.menumeals.MealEditor;
import com.bilgeadam.mobilefoodapp.adapter.CategoryAdapter;
import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.order.shoppingcart.ShoppingCart;
import com.bilgeadam.mobilefoodapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MealMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_menu);
        setTitle("Menümüz");

        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
        FloatingActionButton button = findViewById(R.id.floating_button);

        if (ApplicationMode.currentMode.equals("owner")) {
            // show newItem button to user
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MealEditor.class);
                    startActivity(i);
                }
            });
        } else {
            // hide newItem button from user
            button = findViewById(R.id.floating_button);
            button.hide();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_menu, menu);
        MenuItem mi = menu.findItem(R.id.goToCart);
        if (ApplicationMode.currentMode.equals("owner") || ApplicationMode.currentMode.equals("visitor")) {
            mi.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToCart:
                Intent profileIntent = new Intent(getApplicationContext(), ShoppingCart.class);
                startActivity(profileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
