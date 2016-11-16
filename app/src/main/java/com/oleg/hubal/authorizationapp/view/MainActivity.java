package com.oleg.hubal.authorizationapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.view.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openLoginFragment();
    }

    private void openLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container, LoginFragment.newInstance())
                .commit();
    }
}
