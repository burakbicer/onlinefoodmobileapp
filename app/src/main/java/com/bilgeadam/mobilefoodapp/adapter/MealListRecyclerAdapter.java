package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;

import com.bilgeadam.mobilefoodapp.menumeals.MealAdapter;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.data.DataModel;
import com.bumptech.glide.Glide;
import java.util.List;

public class MealListRecyclerAdapter extends PagerAdapter{
    private List<DataModel> itemList;
    private Context context;

    private MealAdapter adapter;

    private LayoutInflater inflater;

    public MealListRecyclerAdapter(Context context, List<DataModel> itemList){

        this.context=context;
        this.itemList=itemList;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
       if(itemList!=null)
        return itemList.size();
       return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view=inflater.inflate(R.layout.layout_image, container, false);

        ImageView imgView= view.findViewById(R.id.image);
        TextView tv= view.findViewById(R.id.water_mark);

        DataModel gecici=itemList.get(position);

        imgView.setImageResource(gecici.getImageID());
        tv.setText(gecici.getAciklama());



        Glide.with(imgView.getContext())
                    .load(gecici.getUrl())
                    .into(imgView);


        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((FrameLayout) object);

    }
}