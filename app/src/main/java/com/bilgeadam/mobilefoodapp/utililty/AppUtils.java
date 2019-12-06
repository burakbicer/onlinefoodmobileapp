package com.bilgeadam.mobilefoodapp.utililty;


import android.os.Handler;

import androidx.viewpager.widget.ViewPager;

import com.bilgeadam.mobilefoodapp.adapter.MealListRecyclerAdapter;

public final class AppUtils {

    public static void automaticSlide(ViewPager viewPager, MealListRecyclerAdapter mealListRecyclerAdapter){
        final int DELAY = 5000;
        final Handler handler = new Handler();


        Runnable runnable = new Runnable() {
            int page=0;
            boolean first = true;
            @Override
            public void run() {
                if(!first){
                    page = viewPager.getCurrentItem();
                    if((mealListRecyclerAdapter.getCount()-1)== page)
                        page = 0;
                    else
                        page++;

                    viewPager.setCurrentItem(page,true);
                }
                else
                    first = false;

                handler.postDelayed(this, DELAY);
            }
        };

        runnable.run();
    }
}
