package com.bilgeadam.mobilefoodapp.order;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bilgeadam.mobilefoodapp.ApplicationMode;
import com.bilgeadam.mobilefoodapp.order.fragments.DeliveredFragment;
import com.bilgeadam.mobilefoodapp.order.fragments.DeliveringFragment;
import com.bilgeadam.mobilefoodapp.order.fragments.PendingFragment;

public class OrdersCategoryAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;


    public OrdersCategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if(ApplicationMode.deliveredFragment==1) position=2;
        if (position == 0)
            return new PendingFragment();
        else if (position == 1)
            return new DeliveringFragment();
        else
            return new DeliveredFragment();
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        if(ApplicationMode.deliveredFragment==1) return 1;
        else if(ApplicationMode.deliveredFragment==2) return 2;
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(ApplicationMode.deliveredFragment==1) return "ÖNCEKİ SİPARİŞLER";

        if (position == 0)
            return "BEKLEYEN";
        else if (position == 1)
            return "HAZIR";
        else
            return "ÖNCEKİ SİPARİŞLER";
    }
}

