package io.founder.co.mem.moh.mobileymeme.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import io.founder.co.mem.moh.mobileymeme.R;


public class MainFragment extends Fragment implements View.OnClickListener {


    public MainFragment() {
        // Required empty public constructor
    }

    private TextView tvmain;
    private TextView tvTarget;
    private TextView tvPurpose;
    private TextView tvWelcome;

    private Typeface myFont;
    private Typeface myFontText;

    private Button btnSeller, btnBuyer;

    private Animation downtoup,uptodown;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);


        uptodown = AnimationUtils.loadAnimation(getContext(),R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(getContext(),R.anim.downtoup);

        btnSeller = v.findViewById(R.id.btn_saller);
        btnBuyer = v.findViewById(R.id.btn_buyer);

        tvmain = v.findViewById(R.id.tv_main);
        tvWelcome=v.findViewById(R.id.tv_welcome);
        tvTarget = v.findViewById(R.id.tv_target);
        tvPurpose=v.findViewById(R.id.tv_purpose);


        //lottieAnimationView=v.findViewById(R.id.lottieAnimationView);
        //startCheckAnimation();


        tvWelcome.setAnimation(uptodown);
        tvmain.setAnimation(uptodown);

        tvPurpose.setAnimation(downtoup);
        btnBuyer.setAnimation(downtoup);
        btnSeller.setAnimation(downtoup);


        myFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nabila.ttf");
        myFontText = Typeface.createFromAsset(getContext().
                getAssets(), "fonts/Comfortaa.ttf");


        tvTarget.setTypeface(myFontText);
        tvmain.setTypeface(myFont);
        tvWelcome.setTypeface(myFontText);
        btnBuyer.setTypeface(myFontText);
        btnSeller.setTypeface(myFontText);
        tvPurpose.setTypeface(myFontText);



        //events
        btnSeller.setOnClickListener(this);
        btnBuyer.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.btn_buyer:
                fragment = new PostsFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_saller:
                fragment = new EnteringMobileDetailsFragment();
                replaceFragment(fragment);
                break;
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


//    private void startCheckAnimation(){
//        ValueAnimator animator=ValueAnimator.ofFloat(0f,1f).setDuration(800);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                lottieAnimationView.setProgress((float)animation.getAnimatedValue());
//
//            }
//        });
//        if(lottieAnimationView.getProgress()==0f){
//            animator.start();
//        }else{
//            lottieAnimationView.setProgress(0f);
//        }
//    }
}
