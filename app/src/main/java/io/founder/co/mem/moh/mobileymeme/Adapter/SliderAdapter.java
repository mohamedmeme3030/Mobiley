package io.founder.co.mem.moh.mobileymeme.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.founder.co.mem.moh.mobileymeme.Class.Mobile;
import io.founder.co.mem.moh.mobileymeme.R;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Mobile> mobiles;

    public SliderAdapter(Context context, ArrayList<Mobile> mobiles) {
        this.context = context;
        this.mobiles = mobiles;

    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Mobile mobile = mobiles.get(0);
        View view = inflater.inflate(R.layout.item_view_pager, null);

        ImageView imageView = (ImageView) view
                .findViewById(R.id.iv_view_pager);

        switch (position){
            case 0:
                Glide.with(context)
                        .load(mobile.getImage())
                        .into(imageView);

                break;

                case 1:
                    Log.d("tow", "url"+mobile.getImageTow());
                    String uri=mobile.getImageTow();
                    String uriGenerated=uri.substring(8);
                    Log.d("bbbbbbbbbbbbbb", "image tow" +uriGenerated);

                    Glide.with(context)
                            .load(mobile.getImageTow())
                            .into(imageView);

                break;

                 case 2:
                     Log.d("three", "url"+mobile.getImageThree());

                     Glide.with(context)
                             .load(mobile.getImageThree())
                             .into(imageView);

                break;


        }

        Log.d("moeme", "url"+mobile.getImage());

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;

        ImageView imageView;
        imageView = container.findViewById(R.id.iv_view_pager);
        viewPager.removeView(view);


    }
}

