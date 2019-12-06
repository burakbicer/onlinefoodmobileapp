package com.bilgeadam.mobilefoodapp.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.order.OrdersTerminalActivity;
import com.bilgeadam.mobilefoodapp.R;

public class ShopHomeActivity extends AppCompatActivity {

    private Button sOrders, sMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);

        sMenu = findViewById(R.id.sMenuButton);
        sOrders = findViewById(R.id.sOrdersButton);

        sOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationMode.ordersViewer = "owner";
                Intent intent = new Intent(getApplicationContext(), OrdersTerminalActivity.class);
                startActivity(intent);
                sOrders.setAlpha(0.5F);
            }
        });
        sMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ApplicationMode.currentMode = "owner";
                Intent i1 = new Intent(getApplicationContext(), MealMenuActivity.class);
                startActivity(i1);
                sMenu.setAlpha(0.5F);
            }
        });

    }

    @Override
    protected void onResume() {
        // restores transparentation to default

        sMenu.setAlpha(1F);
        sOrders.setAlpha(1F);
        super.onResume();
    }

}
