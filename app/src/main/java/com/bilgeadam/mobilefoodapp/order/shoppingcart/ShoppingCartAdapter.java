package com.bilgeadam.mobilefoodapp.order.shoppingcart;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.user.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShoppingCartAdapter extends ArrayAdapter<ShoppingCartItem> {

    DatabaseReference mReference;


    public ShoppingCartAdapter(@NonNull Context context, @NonNull List<ShoppingCartItem> objects) {
        super(context, 0, objects);
        mReference = FirebaseDatabase.getInstance().getReference("/users/" + UserInfo.userID + "/shoppingCart");
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_shopping_cart_item, parent, false);
        }

        final ShoppingCartItem item = getItem(position);
        TextView itemDatabaseKey = listItemView.findViewById(R.id.sInvisibleKey);
        itemDatabaseKey.setText(item.itemDatabaseKey);
        TextView itemName = listItemView.findViewById(R.id.sItemNameView);
        itemName.setText(item.getName());
        TextView itemQuantity = listItemView.findViewById(R.id.sItemQuantityView);
        itemQuantity.setText("Adet:" + item.getQuantity());
        TextView itemPrice = listItemView.findViewById(R.id.sItemPriceView);
        itemPrice.setText(item.getPrice() + " TL");

        // cancel button removes item from database
        final ImageView cancelView = listItemView.findViewById(R.id.sCancelItem);
//        if(ApplicationMode.currentMode.equals("customer") & (ApplicationMode.orderStatus.equals("pending")
//                || ApplicationMode.orderStatus.equals("delivering")
//                || ApplicationMode.orderStatus.equals("delivered"))) {
//            cancelView.setEnabled(false);
//            cancelView.setVisibility(View.INVISIBLE);
//        }
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartAdapter.this.remove(getItem(position));
                cancelView.setEnabled(false);
                mReference.child(item.itemDatabaseKey).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                        } else {
                            Toast.makeText(getContext(), "Şu anda kaldırılamıyor. Lütfen daha sonra tekrar deneyin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        return listItemView;
    }


}

