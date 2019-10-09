package io.founder.co.mem.moh.mobileymeme.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.founder.co.mem.moh.mobileymeme.Activity.LoginActivity;
import io.founder.co.mem.moh.mobileymeme.Activity.RegisterActivity;
import io.founder.co.mem.moh.mobileymeme.R;


public class HomeFragment extends Fragment implements View.OnClickListener {
    public HomeFragment() {
        // Required empty public constructor
    }


    private TextView tvHome;
    private Typeface myFont;
    private Typeface myFontText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Button btnSignIn, btnSignUp;

        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);


        btnSignIn = v.findViewById(R.id.btn_login_home);
        btnSignUp = v.findViewById(R.id.btn_register_hom);


        tvHome = v.findViewById(R.id.tv_home);
        myFont=Typeface.createFromAsset(getContext().getAssets(),"fonts/Nabila.ttf");
        myFontText =Typeface.createFromAsset(getContext().
                getAssets(),"fonts/Comfortaa.ttf");

        tvHome.setTypeface(myFont);
        btnSignIn.setTypeface(myFontText);
        btnSignUp.setTypeface(myFontText);
        //events

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_home:
                Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.btn_register_hom:
                Intent intentRegister = new Intent(getContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

