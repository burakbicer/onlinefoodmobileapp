package com.bilgeadam.mobilefoodapp.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.order.shoppingcart.ShoppingCartAdapter;
import com.bilgeadam.mobilefoodapp.order.shoppingcart.ShoppingCartItem;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.user.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoDialog extends Dialog {
    public static final String TAG = "orderInfoDialog";

    private String positiveToastMessage;
    private String negativeToastMessage;
    private Activity c;
    private String orderId;
    private String userId;
    private DatabaseReference fromReference;
    private DatabaseReference toReference;
    private DatabaseReference mReference;
    private ChildEventListener mChildEventListener;
    private TextView userNameView, userPhoneView, userAddressView;
    private ListView listView;
    private Button accept, decline;
    private ShoppingCartAdapter adapter;


    public OrderInfoDialog(Activity a, String orderId, String userId) {
        super(a);
        this.c = a;
        this.orderId = orderId;
        this.userId = userId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_info_window);

        userNameView = findViewById(R.id.oi_user_name);
        userAddressView = findViewById(R.id.oi_user_address);
        userPhoneView = findViewById(R.id.oi_user_number);
        listView = findViewById(R.id.oiItems_list);
        accept = findViewById(R.id.accept);
        decline = findViewById(R.id.decline);


        if (!ApplicationMode.checkConnectivity(getContext())) {
            // no internet, disallow writes
            accept.setEnabled(false);
            decline.setEnabled(false);
            accept.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
            decline.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
            Toast.makeText(getContext(), "İnternet bağlantısı yok", Toast.LENGTH_SHORT).show();

        }


        List<ShoppingCartItem> items = new ArrayList<>();
        adapter = new ShoppingCartAdapter(getContext(), items);
        listView.setAdapter(adapter);

        Log.i(TAG, ApplicationMode.orderStatus);
        switch (ApplicationMode.orderStatus) {
            case "pending":
                fromReference = FirebaseDatabase.getInstance().getReference("/orders/pending/-" + orderId);
                toReference = FirebaseDatabase.getInstance().getReference("/orders/delivering");
                if (ApplicationMode.ordersViewer == "owner") {
                    accept.setText("SİPARİŞİ KABUL ET");
                    decline.setText("SİPARİŞİ REDDET");
                    positiveToastMessage = "Sipariş kabul edildi.";
                    negativeToastMessage = "Sipariş reddedildi.";
                } else {
                    accept.setVisibility(View.GONE);
                    decline.setText("SİPARİŞİ İPTAL ET");
                    negativeToastMessage = "Sipariş iptal edildi.";
                }

                break;
            case "delivering":
                fromReference = FirebaseDatabase.getInstance().getReference("/orders/delivering/-" + orderId);
                toReference = FirebaseDatabase.getInstance().getReference("/orders/delivered");
                if (ApplicationMode.ordersViewer == "owner") {
                    accept.setText("SİPARİŞİ TESLİM ET");
                    decline.setText("SİPARİŞİ İPTAL ET");
                    positiveToastMessage = "Sipariş teslim edildi.";
                    negativeToastMessage = "Sipariş iptal edildi.";
                } else {
                    accept.setVisibility(View.GONE);
                    decline.setVisibility(View.GONE);
                }

                break;
            case "delivered":


                fromReference = FirebaseDatabase.getInstance().getReference("/orders/delivered/-" + orderId);
                toReference = FirebaseDatabase.getInstance().getReference("/orders/delivering");
                accept.setVisibility(View.GONE);
                decline.setText("SİPARİŞİ SİL");
                negativeToastMessage = "Sipariş silindi.";

                break;

        }
        mReference = FirebaseDatabase.getInstance().getReference("/users/" + userId);
        loadAndSetCurrentUser(userId);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApplicationMode.currentMode.equals("owner") || ApplicationMode.currentMode.equals("customer")) {
                    moveFirebaseRecord();

                } else {
                    Toast.makeText(getContext(), "Bu işlem için yetkili değilsiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApplicationMode.currentMode.equals("owner") || ApplicationMode.currentMode.equals("customer")) {
                    fromReference.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Toast.makeText(getContext(), negativeToastMessage, Toast.LENGTH_SHORT).show();
                                dismiss();

                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Bu işlem için yetkili değilsiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void loadAndSetCurrentUser(String userId) {
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    User currentUser = dataSnapshot.getValue(User.class);
                    Log.i(TAG, mReference.toString());
                    userNameView.setText(currentUser.getUserName());
                    userAddressView.setText(currentUser.getUserAddress());
                    userPhoneView.setText(currentUser.getUserPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Error loading user data");
            }
        });

    }


    public void moveFirebaseRecord() {
        Log.i(TAG, fromReference.toString() + "   " + toReference.toString());
        fromReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toReference.child("-" + orderId).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        fromReference.removeValue();
                        Toast.makeText(getContext(), positiveToastMessage, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, databaseError.toString());
            }
        });
    }


    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ShoppingCartItem item = dataSnapshot.getValue(ShoppingCartItem.class);
                    adapter.add(item);

                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            fromReference.child("allItems").addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            fromReference.child("allItems").removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }


    @Override
    protected void onStop() {
        adapter.clear();
        detachDatabaseReadListener();
        super.onStop();
    }

    @Override
    public void onStart() {
        attachDatabaseReadListener();
        super.onStart();
    }
}
