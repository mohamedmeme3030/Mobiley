package io.founder.co.mem.moh.mobileymeme.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.founder.co.mem.moh.mobileymeme.Base.MyBaseActivity;
import io.founder.co.mem.moh.mobileymeme.R;


public class LoginActivity extends MyBaseActivity {

    //widgets

    private Button btnSignInLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvLogin;
    private TextView tvSignUp;
    private TextView tvMain;
    private ProgressBar progressBar;
    //private LottieAnimationView lottieAnimationView;


    private Typeface myFont;
    private Typeface myFontText;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Init Firebase
        auth = FirebaseAuth.getInstance();


        //Init View
        btnSignInLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_sign_up_l);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.tv_login);
        tvMain = findViewById(R.id.tv_main_l);

        // startCheckAnimation();


        myFont = Typeface.createFromAsset(LoginActivity.this.
                getAssets(), "fonts/Comfortaa.ttf");
        myFontText = Typeface.createFromAsset(this.
                getAssets(), "fonts/Comfortaa.ttf");


        tvLogin.setTypeface(myFont);
        btnSignInLogin.setTypeface(myFontText);
        tvMain.setTypeface(myFontText);
        tvSignUp.setTypeface(myFontText);
        etEmail.setTypeface(myFont);
        etPassword.setTypeface(myFont);
        //------------------------------//


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentSignUp);
                finish();
            }
        });

        btnSignInLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFirebase();
            }
        });



    }

    //login with firebase
    public void loginWithFirebase() {
        String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (isConnectedToInternet()) {
            //authenticate user
            //progressBar.setVisibility(View.VISIBLE);
            try {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {



                                }
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection ", Toast.LENGTH_SHORT).show();
        }
    }


    //Check Internet
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
