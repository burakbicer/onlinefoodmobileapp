package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bilgeadam.mobilefoodapp.fragments.CorbaFragment;
import com.bilgeadam.mobilefoodapp.fragments.TatlilarFragment;
import com.bilgeadam.mobilefoodapp.fragments.IceceklerFragment;
import com.bilgeadam.mobilefoodapp.fragments.KampanyaFragment;
import com.bilgeadam.mobilefoodapp.fragments.YemekFragment;


public class CategoryAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;


    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new CorbaFragment();
        else if (position == 1)
            return new YemekFragment();
        else if (position == 2)
            return new TatlilarFragment();
        else if (position == 3)
            return new IceceklerFragment();
        else
            return new KampanyaFragment();

    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {

        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "ÇORBALAR";
        else if (position == 1)
            return "YEMEKLER";
        else if (position == 2)
            return "TATLILAR";
        else if (position == 3)
            return "İÇECEKLER";
        else
            return "KAMPANYALAR";
    }
}
