package com.example.heathyapp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.heathyapp4.R;

public class OffersAdapter extends PagerAdapter {
    Context context;

    int images[] = {

            R.drawable.layerlist_drawable,
            R.drawable.layerlist_drawable,
            R.drawable.layerlist_drawable,

    };

    int headings[] = {



            R.string.title_One_Offer,
            R.string.title_two_Offers,
            R.string.title_three_Offers,

    };

    int description[] = {

            R.string.get_off_one,
            R.string.get_off_two,
            R.string.get_off_three,

    };

    public OffersAdapter(Context context){

        this.context = context;

    }


    public int getCount() {
        return  headings.length;
    }


    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_offers,container,false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.offerImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.titleOffer);
        TextView slideDesciption = (TextView) view.findViewById(R.id.getOffTxt);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDesciption.setText(description[position]);

        container.addView(view);

        return view;

    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);

    }
}
