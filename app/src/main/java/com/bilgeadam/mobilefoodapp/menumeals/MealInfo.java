package com.bilgeadam.mobilefoodapp.menumeals;

import android.content.Intent;

public final class MealInfo {
    public static String itemName;
    public static String itemDescription;
    public static String itemPrice;
    public static String itemCategory;
    public static String imageUrl;
    public static String itemId;


    public static void setMealInfo(Meal meal) {
        itemName = meal.getItemName();
        itemDescription = meal.getItemDescription();
        itemPrice = meal.getItemPrice();
        itemCategory = meal.getItemCategory();
        imageUrl = meal.getImageUrl();
        itemId = meal.itemId;
    }

    public static void setMealInfo(Intent intent) {
        itemName = intent.getStringExtra("itemName");
        itemDescription = intent.getStringExtra("itemDescription");
        itemPrice = intent.getStringExtra("itemPrice");
        itemCategory = intent.getStringExtra("itemCategory");
        imageUrl = intent.getStringExtra("imageUrl");
        itemId = intent.getStringExtra("itemId");
    }

    public static Meal toItem() {
        return new Meal(itemName, itemDescription, itemPrice, itemCategory, imageUrl, itemId);
    }

}
