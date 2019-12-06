package com.bilgeadam.mobilefoodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.activity.Login.WelcomeScreenActivity;
import com.bilgeadam.mobilefoodapp.user.UserInfo;
import com.bilgeadam.mobilefoodapp.order.OrdersTerminalActivity;
import com.bilgeadam.mobilefoodapp.order.shoppingcart.ShoppingCart;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.adapter.MealListRecyclerAdapter;
import com.bilgeadam.mobilefoodapp.custom.ClickableViewPager;
import com.bilgeadam.mobilefoodapp.data.DataModel;
import com.bilgeadam.mobilefoodapp.utililty.AppUtils;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;



public class MainActivity extends AppCompatActivity {

    private List<DataModel> campaignMealList;
    private SwipeRefreshLayout swipeRefresh;

    private static final String TAG = "Main Activity";
    static boolean calledAlready = false;

    private MenuItem menuItemProfile;
    private MenuItem menuItemCart;
    private MenuItem menuItemMyOrders;
    private MenuItem menuItemManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //enable local data storage
        if (!calledAlready) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }

        Button viewMenuButton = findViewById(R.id.mealbtn);
        Button viewLoginButton = findViewById(R.id.loginbtn);
        Button viewOrdersbtn = findViewById(R.id.ordersbtn);
        Button viewPreviousOrdersbtn = findViewById(R.id.previousordersbtn);

        viewMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open menu
                Intent i1 = new Intent(getApplicationContext(), MealMenuActivity.class);
                startActivity(i1);
            }
        });

        viewLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Login
                if(UserInfo.userEmail==null || UserInfo.userPassword==null) {
                    Intent i1 = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                    startActivity(i1);
                }else
                {
                    Intent profileIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
                    startActivity(profileIntent);
                }

            }
        });

        viewOrdersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserInfo.userEmail==null || UserInfo.userPassword==null) {
                    Intent i1 = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                    startActivity(i1);
                }else
                {
                    if(ApplicationMode.currentMode.equals("owner")){
                        ApplicationMode.ordersViewer = "owner";
                    }else {
                        ApplicationMode.ordersViewer = "customer";
                    }
                    ApplicationMode.deliveredFragment=2;
                    Intent myOrders = new Intent(getApplicationContext(), OrdersTerminalActivity.class);
                    startActivity(myOrders);
                }
            }
        });

        viewPreviousOrdersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserInfo.userEmail==null || UserInfo.userPassword==null) {
                    Intent i1 = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                    startActivity(i1);
                }else
                {
                    if(ApplicationMode.currentMode.equals("owner")){
                        ApplicationMode.ordersViewer = "owner";
                    }else {
                        ApplicationMode.ordersViewer = "customer";
                    }
                    ApplicationMode.deliveredFragment=1;
                    Intent myPrevOrders = new Intent(getApplicationContext(), OrdersTerminalActivity.class);
                    startActivity(myPrevOrders);
                }
            }
        });

        configureSlider();

        swipeRefresh = findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> {
            this.recreate();
            swipeRefresh.setRefreshing(false);
        });
    }

    private void setMainActivityView() {
        if(ApplicationMode.currentMode.equals("visitor")){
            menuItemCart.setVisible(false);
            menuItemProfile.setVisible(false);
            menuItemMyOrders.setVisible(false);
            menuItemManager.setVisible(false);
        }else if (ApplicationMode.currentMode.equals("customer")){
            menuItemCart.setVisible(true);
            menuItemProfile.setVisible(true);
            menuItemMyOrders.setVisible(true);
            menuItemManager.setVisible(false);
        }else if(ApplicationMode.currentMode.equals("owner")){
            menuItemCart.setVisible(false);
            menuItemProfile.setVisible(true);
            menuItemMyOrders.setVisible(false);
            menuItemManager.setVisible(true);
        }
    }

    private void configureSlider() {
        ClickableViewPager viewPager = findViewById(R.id.image_pager);
        MealListRecyclerAdapter mealListRecyclerAdapter = new MealListRecyclerAdapter(this, DataModel.getDataList());
        campaignMealList=DataModel.getDataList();

        viewPager.setAdapter(mealListRecyclerAdapter);
        viewPager.setOnItemClickListener(position -> {
            if(!campaignMealList.isEmpty()) {
                DataModel gecici=campaignMealList.get(position);
                Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
                intent.putExtra(getString(R.string.imageUrl), gecici.getUrl());
                intent.putExtra(getString(R.string.itemName), gecici.getBaslik());
                intent.putExtra(getString(R.string.itemPrice), gecici.getPrice()); // separates the TL part
                intent.putExtra(getString(R.string.itemDescription), gecici.getAciklama());
                intent.putExtra(getString(R.string.itemCategory), "Kampanyalar");
                intent.putExtra(getString(R.string.itemId), gecici.getimageItemId());
                startActivity(intent);
            }
        });


        AppUtils.automaticSlide(viewPager, mealListRecyclerAdapter);
        CircleIndicator circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        if(UserInfo.isManager==2) {
            ApplicationMode.currentMode="owner";
        }else if(UserInfo.isManager==1){
            ApplicationMode.currentMode="customer";
        }else{
            ApplicationMode.currentMode="visitor";
        }
        if(menuItemProfile!=null)
            setMainActivityView();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menuItemProfile = menu.findItem(R.id.profile_option);
        menuItemCart = menu.findItem(R.id.shoppingCart);
        menuItemMyOrders = menu.findItem(R.id.myOrders);
        menuItemManager = menu.findItem(R.id.shop_option);

        setMainActivityView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_option:
                Intent profileIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.shop_option:
                ApplicationMode.currentMode = "owner";
                Intent i = new Intent(getApplicationContext(), ShopHomeActivity.class);
                startActivity(i);
                return true;
            case R.id.shoppingCart:
                Intent shoppingCartIntent = new Intent(getApplicationContext(), ShoppingCart.class);
                startActivity(shoppingCartIntent);
                return true;
            case R.id.myOrders:
                ApplicationMode.ordersViewer = "customer";
                Intent myOrders = new Intent(getApplicationContext(), OrdersTerminalActivity.class);
                startActivity(myOrders);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
