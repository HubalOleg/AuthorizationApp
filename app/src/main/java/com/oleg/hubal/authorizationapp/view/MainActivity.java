package com.oleg.hubal.authorizationapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.Utils;
import com.oleg.hubal.authorizationapp.view.login.LoginFragment;
import com.oleg.hubal.authorizationapp.view.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.UserLoginListener,
        ProfileFragment.UserLogoutListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Utils.isLoggedIn(AccessToken.getCurrentAccessToken())) {
            showProfileFragment();
        } else {
            showLoginFragment();
        }
    }

    @Override
    public void showLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.fl_fragment_container, LoginFragment.newInstance())
                .commit();
    }

    @Override
    public void showProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.fl_fragment_container, ProfileFragment.newInstance())
                .commit();
    }

}
