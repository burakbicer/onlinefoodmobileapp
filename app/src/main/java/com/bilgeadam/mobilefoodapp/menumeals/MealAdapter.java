package com.bilgeadam.mobilefoodapp.menumeals;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bilgeadam.mobilefoodapp.R;

import java.util.List;

public class MealAdapter extends ArrayAdapter<Meal> {

    public MealAdapter(@NonNull Context context, @NonNull List<Meal> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_item_view, parent, false);
        }

        Meal meal = getItem(position);

        TextView imageUrl = listItemView.findViewById(R.id.invisible_item_image_url);
        imageUrl.setText(meal.getImageUrl());
        TextView itemId = listItemView.findViewById(R.id.invisible_item_id_view);
        itemId.setText(meal.itemId);
        TextView itemName = listItemView.findViewById(R.id.item_name_view);
        itemName.setText(meal.getItemName());
        TextView itemDescription = listItemView.findViewById(R.id.item_description_view);
        itemDescription.setText(meal.getItemDescription());
        TextView itemPrice = listItemView.findViewById(R.id.item_price_view);
        itemPrice.setText(meal.getItemPrice() + " TL");
        ImageView itemImage = listItemView.findViewById(R.id.item_image_view);
        if (meal.getImageUrl() != null) {
            Glide.with(itemImage.getContext())
                    .load(meal.getImageUrl())
                    .into(itemImage);
        } else {
            itemImage.setImageResource(R.drawable.no_image);
        }

        return listItemView;
    }
}
