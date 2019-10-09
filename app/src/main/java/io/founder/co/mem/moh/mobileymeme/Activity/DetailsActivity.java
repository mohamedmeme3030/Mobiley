package io.founder.co.mem.moh.mobileymeme.Activity;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

import io.founder.co.mem.moh.mobileymeme.Adapter.SliderAdapter;
import io.founder.co.mem.moh.mobileymeme.Class.Mobile;
import io.founder.co.mem.moh.mobileymeme.R;


public class DetailsActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SliderAdapter sliderAdapter;
    private ArrayList<Mobile> mMobiles;
    private MapView mapView;

    private TextView tvBrand;
    private TextView tvColor;
    private TextView tvPrice;
    private TextView tvCondition;
    private TextView tvDetail;
    private TextView tvLocation;
    private TextView tvStorageCapacity;
    private TextView tvName;
    private TextView tvDetails;
    private ImageButton btnCalling;
    private TextView tvMain;
    private String ID;


    private Mobile postMobile;
    private ImageView ivDetail;


    private Typeface myFont;
    private Typeface myFontText;

    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibW9oYW1lZG1lbWUzMDMwIiwiYSI6ImNqdDV3MmR0dTBhdjI0NHAzanlxZHJuaDUifQ.BZULZOMLY5lSIg-EOCepzw");
        setContentView(R.layout.activity_details);


        //-----------------------------------------//
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });
            }
        });


        //Init

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);


        tvBrand = findViewById(R.id.tv_brand_d);
        tvCondition = findViewById(R.id.tv_condition_d);
        tvColor = findViewById(R.id.tv_color_d);
        tvPrice = findViewById(R.id.tv_price_d);
        tvStorageCapacity = findViewById(R.id.tv_storage_capacity_d);
        tvDetail = findViewById(R.id.tv_detail_d);
        tvName = findViewById(R.id.tv_name);
        btnCalling = findViewById(R.id.btn_calling);


        //font detail activity
        tvDetails = findViewById(R.id.tv_detail);
        tvMain = findViewById(R.id.tv_main);


        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Comfortaa.ttf");
        myFontText = Typeface.createFromAsset(this.getAssets(), "fonts/Nabila.ttf");
        tvDetails.setTypeface(myFont);
        tvMain.setTypeface(myFontText);

        //ivDetail = findViewById(R.id.iv_mobile_d);


        postMobile = (Mobile) getIntent().getSerializableExtra("mobile");
        mMobiles = new ArrayList<>();
        mMobiles.add(postMobile);
        initSliderAdapter(mMobiles);


//        Glide.with(this).load(postMobile.getImage()).into(ivDetail);

        ID = postMobile.getID();
        tvBrand.setText(postMobile.getBrand() + " " + postMobile.getModel());
        tvCondition.setText(postMobile.getCondition());
        tvPrice.setText(postMobile.getPrice());
        tvColor.setText(postMobile.getColor());
        tvStorageCapacity.setText(postMobile.getStorageCapacity());
        tvDetail.setText(postMobile.getDetail());
        tvName.setText(postMobile.getName());
        phone = postMobile.getPhone();


        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profile = new Intent(DetailsActivity.this, ProfileActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("userName", tvName.getText().toString());
                data.putSerializable("ID", ID);
                profile.putExtras(data);
                startActivity(profile);

            }
        });


        btnCalling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone(phone);
            }
        });
    }


    private void dialContactPhone(final String phoneNumber) {

//Every Action of the intent have the ui such as the action dial
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel" + phoneNumber));

//resolveActivity To handel the Activity of Intent that  go It
        if (intentCall.resolveActivity(getPackageManager()) != null) {

            startActivity(intentCall);

        } else {

            Toast.makeText(this, "No Application Found ", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void initSliderAdapter(List<Mobile> mobiles) {

        mMobiles = (ArrayList<Mobile>) mobiles;
        sliderAdapter = new SliderAdapter(DetailsActivity.this, mMobiles);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setAdapter(sliderAdapter);
    }


}
