package com.bilgeadam.mobilefoodapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.menumeals.Meal;
import com.bilgeadam.mobilefoodapp.menumeals.MealAdapter;
import com.bilgeadam.mobilefoodapp.menumeals.MealEditor;
import com.bilgeadam.mobilefoodapp.activity.MealDetailActivity;

import com.bilgeadam.mobilefoodapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TatlilarFragment extends Fragment {
    private DatabaseReference mReference;
    private ChildEventListener mChildEventListener;

    private ListView listView;
    private MealAdapter adapter;

    public TatlilarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_with_empty_view, container, false);

        listView = rootView.findViewById(R.id.items_list);
        View emptyView = rootView.findViewById(R.id.empty_view);


        if (!ApplicationMode.checkConnectivity(getActivity())) {
            TextView emptyTitle = rootView.findViewById(R.id.empty_title_text);
            emptyTitle.setText("Lütfen internet bağlantınızı kontrol edin");

        }
        listView.setEmptyView(emptyView);
        mReference = FirebaseDatabase.getInstance().getReference("/items/" + getString(R.string.tatlilar));

        //creating an empty list view to set up the adapter
        //later on the onChildAdded() method in attachDatabaseReadListener() keeps updating our adapter
        List<Meal> meals = new ArrayList<>();
        adapter = new MealAdapter(getActivity(), meals);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView itemNameView = view.findViewById(R.id.item_name_view);
                TextView itemDescriptionView = view.findViewById(R.id.item_description_view);
                TextView itemPriceView = view.findViewById(R.id.item_price_view);
                TextView itemIdVIew = view.findViewById(R.id.invisible_item_id_view);
                TextView imageUrl = view.findViewById(R.id.invisible_item_image_url);
                if (ApplicationMode.currentMode.equals("owner")) {

                    // allow owner to update Meal or visitor to view
                    Intent intent = new Intent(getContext(), MealEditor.class);
                    intent.putExtra(getString(R.string.imageUrl), imageUrl.getText().toString());
                    intent.putExtra(getString(R.string.itemName), itemNameView.getText().toString());
                    intent.putExtra(getString(R.string.itemPrice), itemPriceView.getText().toString().split(" ")[0]); // separates the TL part
                    intent.putExtra(getString(R.string.itemDescription), itemDescriptionView.getText().toString());
                    intent.putExtra(getString(R.string.itemCategory), getString(R.string.tatlilar));
                    intent.putExtra(getString(R.string.itemId), itemIdVIew.getText().toString());
                    startActivity(intent);
                } else {
                    // allow user to view MealInfo
                    Intent intent = new Intent(getContext(), MealDetailActivity.class);
                    intent.putExtra(getString(R.string.imageUrl), imageUrl.getText().toString());
                    intent.putExtra(getString(R.string.itemName), itemNameView.getText().toString());
                    intent.putExtra(getString(R.string.itemPrice), itemPriceView.getText().toString().split(" ")[0]); // separates the TL part
                    intent.putExtra(getString(R.string.itemDescription), itemDescriptionView.getText().toString());
                    intent.putExtra(getString(R.string.itemCategory), getString(R.string.tatlilar));
                    intent.putExtra(getString(R.string.itemId), itemIdVIew.getText().toString());
                    startActivity(intent);

                }
            }
        });

        return rootView;
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Meal meal = dataSnapshot.getValue(Meal.class);
                    meal.itemId = dataSnapshot.getKey();
                    adapter.add(meal);

                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    //reattaching updates list in UI
                    adapter.clear();
                    detachDatabaseReadListener();
                    attachDatabaseReadListener();
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    // reattaching the listener updates our list in UI
                    adapter.clear();
                    detachDatabaseReadListener();
                    attachDatabaseReadListener();
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onPause() {
        adapter.clear();
        detachDatabaseReadListener();
        super.onPause();
    }

    @Override
    public void onStart() {
        attachDatabaseReadListener();
        super.onStart();
    }
}
