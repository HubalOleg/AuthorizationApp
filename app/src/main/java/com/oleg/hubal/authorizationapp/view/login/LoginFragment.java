package com.oleg.hubal.authorizationapp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.oleg.hubal.authorizationapp.R;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 16.11.2016.
 */

public class LoginFragment extends Fragment implements LoginView {

    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();

        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView tv = (TextView) view.findViewById(R.id.facebook_text);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = (LoginButton) view.findViewById(R.id.btn_facebook_login);
        mLoginButton.setReadPermissions("email");
        mLoginButton.setFragment(this);
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tv.setText(loginResult.getAccessToken().getUserId());
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: ");
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode + " ");
    }
}
