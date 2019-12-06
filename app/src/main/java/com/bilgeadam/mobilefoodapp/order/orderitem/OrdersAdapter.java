package com.bilgeadam.mobilefoodapp.order.orderitem;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.user.User;
import com.bilgeadam.mobilefoodapp.user.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {
    public static final String TAG = "orderAdapter";
    private DatabaseReference mReference;



    public OrdersAdapter(@NonNull Context context, @NonNull List<Order> objects) {
        super(context, 0, objects);
        mReference = FirebaseDatabase.getInstance().getReference("/users");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View orderItemView = convertView;
        if (orderItemView == null) {
            orderItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_item_order_view, parent, false);
        }

        Order orderItem = getItem(position);


        TextView userIdVIew = orderItemView.findViewById(R.id.soUserID);
        TextView userIdVIew2 = orderItemView.findViewById(R.id.soUserID2);
        userIdVIew.setText(orderItem.getUserId());
        userIdVIew2.setText(UserInfo.userName);

        TextView orderIdVIew2 = orderItemView.findViewById(R.id.soOrderID2);
        orderIdVIew2.setText(orderItem.getOrderIDstr());


        mReference = FirebaseDatabase.getInstance().getReference("/users/"+orderItem.getUserId());
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUserX = dataSnapshot.getValue(User.class);
                if (currentUserX != null) {
                    userIdVIew2.setText(currentUserX.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Error loading user data");
            }
        });
        //

        TextView orderDateView = orderItemView.findViewById(R.id.soDate);
        orderDateView.setText(orderItem.getDate());



        TextView orderIdVIew = orderItemView.findViewById(R.id.soOrderID);
        orderIdVIew.setText(orderItem.orderID.substring(1));

        TextView totalPrice = orderItemView.findViewById(R.id.soTotalPrice);
        totalPrice.setText("Toplam Tutar \n" + orderItem.getTotalPrice() + "TL");
        return orderItemView;

    }

}
