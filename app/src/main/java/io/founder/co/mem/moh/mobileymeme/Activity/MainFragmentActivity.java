package io.founder.co.mem.moh.mobileymeme.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.founder.co.mem.moh.mobileymeme.Fragment.EnteringMobileDetailsFragment;
import io.founder.co.mem.moh.mobileymeme.Fragment.HomeFragment;
import io.founder.co.mem.moh.mobileymeme.Fragment.MainFragment;
import io.founder.co.mem.moh.mobileymeme.Fragment.PostsFragment;
import io.founder.co.mem.moh.mobileymeme.R;


public class MainFragmentActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);


        //loading the default fragment
        loadFragment(new MainFragment());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView mMainNav = findViewById(R.id.bottom_navigation_);
        mMainNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new MainFragment();
                break;
            case R.id.navigation_brows:
                fragment = new PostsFragment();
                break;
            case R.id.navigation_account:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_add:
                fragment = new EnteringMobileDetailsFragment();
                break;
        }

        return loadFragment(fragment);

    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

