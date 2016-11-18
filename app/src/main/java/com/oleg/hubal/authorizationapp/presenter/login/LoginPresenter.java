package com.oleg.hubal.authorizationapp.presenter.login;

import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.oleg.hubal.authorizationapp.Constants;
import com.oleg.hubal.authorizationapp.view.login.LoginViewContract;

/**
 * Created by User on 16.11.2016.
 */

public class LoginPresenter implements LoginPresenterContract {

    private LoginViewContract mView;
    private CallbackManager mCallbackManager;


    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mView.userLogin(Constants.LOGIN_STATUS_FACEBOOK);
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
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFacebookLogin() {
        LoginManager.getInstance().registerCallback(mCallbackManager, mFacebookCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
