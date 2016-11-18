package com.oleg.hubal.authorizationapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oleg.hubal.authorizationapp.view.login.LoginFragment;
import com.oleg.hubal.authorizationapp.view.login.LoginViewContract;
import com.oleg.hubal.authorizationapp.view.profile.ProfileFragment;
import com.oleg.hubal.authorizationapp.view.profile.ProfileViewContract;

public class MainActivity extends AppCompatActivity implements
        LoginViewContract.UserLoginListener,
        ProfileViewContract.UserLogoutListener {

    private int mLoginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginStatus = getLoginStatus();

        if (mLoginStatus == Constants.LOGIN_STATUS_NONE) {
            showLoginFragment();
        } else {
            showProfileFragment();
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

    private int getLoginStatus() {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        return sPref.getInt(Constants.PREF_LOGIN_STATUS, Constants.LOGIN_STATUS_NONE);
    }

}
