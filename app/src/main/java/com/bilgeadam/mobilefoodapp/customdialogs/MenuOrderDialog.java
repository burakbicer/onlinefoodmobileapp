package com.bilgeadam.mobilefoodapp.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgeadam.mobilefoodapp.menumeals.MealInfo;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.order.shoppingcart.ShoppingCartItem;
import com.bilgeadam.mobilefoodapp.user.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuOrderDialog extends Dialog implements android.view.View.OnClickListener {
    private Activity c;
    private TextView orderQuantityView;
    private Button increment, decrement, addToCart;

    private DatabaseReference mReference;
    private DatabaseReference.CompletionListener listener;

    private int orderQuantity = 1;
    private ShoppingCartItem item;

    public MenuOrderDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_dialog);
        addToCart = findViewById(R.id.oAddToCartButton);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        orderQuantityView = findViewById(R.id.oOrderQuantity);
        addToCart.setOnClickListener(this);

        mReference = FirebaseDatabase.getInstance().getReference("/users/" + UserInfo.userID + "/shoppingCart");
        listener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                addToCart.setEnabled(true);
                addToCart.setBackgroundColor(c.getResources().getColor(R.color.button_pink));
                if (databaseError == null) {
                    Toast.makeText(c.getApplicationContext(), "Sepete eklendi", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(c.getApplicationContext(), "Sepete eklenirken hata oluştu!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }
        };

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderQuantity != 10) {
                    orderQuantity += 1;
                    orderQuantityView.setText(String.valueOf(orderQuantity));
                }
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderQuantity != 1) {
                    orderQuantity -= 1;
                    orderQuantityView.setText(String.valueOf(orderQuantity));
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        addToCart.setEnabled(false);
        addToCart.setBackgroundColor(c.getResources().getColor(R.color.grey));
        item = new ShoppingCartItem(MealInfo.itemName, MealInfo.itemPrice, MealInfo.itemId, String.valueOf(orderQuantity));
        String key = mReference.push().getKey();
        mReference.child(key).setValue(item, listener);

    }

}

