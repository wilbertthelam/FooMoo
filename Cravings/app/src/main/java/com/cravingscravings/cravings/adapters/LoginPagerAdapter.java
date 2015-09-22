package com.cravingscravings.cravings.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cravingscravings.cravings.R;

/**
 * Created by linse on 8/31/2015.
 * Adapter for Page Viewer in LoginActivity
 */
public class LoginPagerAdapter extends PagerAdapter{

    int[] slides; // Holds the drawable resource IDs of the slides
    LayoutInflater mLayoutInflater; // Inflates the view for the slide image

    public LoginPagerAdapter(Context context, int[] slides) {
        this.slides = slides;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return slides.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_login_slide, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(slides[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
