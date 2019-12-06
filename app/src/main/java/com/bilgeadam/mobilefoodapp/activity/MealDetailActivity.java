package com.bilgeadam.mobilefoodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.menumeals.MealInfo;
import com.bilgeadam.mobilefoodapp.activity.Login.WelcomeScreenActivity;
import com.bumptech.glide.Glide;
import com.bilgeadam.mobilefoodapp.customdialogs.MenuOrderDialog;
import com.bilgeadam.mobilefoodapp.R;

public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_meal);
        MealInfo.setMealInfo(getIntent());

        ImageView itemImage = findViewById(R.id.oItemImageView);
        TextView itemName = findViewById(R.id.oItemNameView);
        TextView itemPrice = findViewById(R.id.oItemPriceView);
        TextView itemCategory = findViewById(R.id.oItemCategoryView);
        TextView itemDescription = findViewById(R.id.oItemDescriptionView);
        Button orderButton = findViewById(R.id.orderButton);
        itemName.setText(MealInfo.itemName);
        itemPrice.setText(MealInfo.itemPrice);
        itemDescription.setText(MealInfo.itemDescription);
        itemCategory.setText(MealInfo.itemCategory);
        Glide.with(itemImage.getContext())
                .load(MealInfo.imageUrl)
                .into(itemImage);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ApplicationMode.currentMode.equals("customer")){
                    MenuOrderDialog orderDialog = new MenuOrderDialog(MealDetailActivity.this);
                    orderDialog.show();
                }else{
                    Intent i1 = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                    startActivity(i1);
                    finish();
                }
            }
        });
    }
}
