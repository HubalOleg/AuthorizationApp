package com.oleg.hubal.authorizationapp.presenter.login;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.oleg.hubal.authorizationapp.view.login.LoginViewContract;

/**
 * Created by User on 16.11.2016.
 */

public class LoginPresenter implements LoginPresenterContract {

    private static final String TAG = "LoginPresenter";

    private LoginViewContract mView;

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mView.showProfileFragment();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            mView.showError();
        }
    };

    public LoginPresenter(LoginViewContract view) {
        mView = view;
    }

    @Override
    public FacebookCallback<LoginResult> getFacebookLoginCallback() {
        return mFacebookCallback;
    }

    @Override
    public void onStop() {

    }
}
