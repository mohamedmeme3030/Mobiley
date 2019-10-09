package io.founder.co.mem.moh.mobileymeme.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.founder.co.mem.moh.mobileymeme.R;


public class SplashActivity extends AppCompatActivity {
    Typeface myFont;
    TextView tvPower;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mAuth = FirebaseAuth.getInstance();
        tvPower=findViewById(R.id.power);
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();


        //tvSplash = findViewById(R.id.tv_splach);
        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Cherry.ttf");
        tvPower.setTypeface(myFont);
//        tvSplash.setTypeface(myFont);
        //type font
        //splash
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(),MainFragmentActivity
                            .class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException error) {

                    error.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void VerifyUserExistance() {
        String currentUserID = mAuth.getCurrentUser().getUid();

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if ((dataSnapshot.child("name").exists())) {
                    Toast.makeText(SplashActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }else {
                    sendUserToProfileActivity();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void sendUserToProfileActivity() {

        Intent intentProfile = new Intent(SplashActivity.this, LoginActivity.class);
        intentProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentProfile);
        finish();
    }

    private void sendUserToLoginActivity() {
        Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
        intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
    }
}
