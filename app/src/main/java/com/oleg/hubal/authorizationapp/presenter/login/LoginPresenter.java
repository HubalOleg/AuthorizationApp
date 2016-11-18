package com.oleg.hubal.authorizationapp.presenter.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.oleg.hubal.authorizationapp.Constants;
import com.oleg.hubal.authorizationapp.view.login.LoginViewContract;

/**
 * Created by User on 16.11.2016.
 */

public class LoginPresenter implements LoginPresenterContract {

    private LoginViewContract mView;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;

// CALLBACK AND LISTENER
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

    private GoogleApiClient.OnConnectionFailedListener mGoogleConnectionListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            mView.showError();
        }
    };
// CALLBACK AND LISTENER END


    public LoginPresenter(LoginViewContract view) {
        mView = view;
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onFacebookLogin() {
        LoginManager.getInstance().registerCallback(mCallbackManager, mFacebookCallback);
    }

    @Override
    public void onGooglePlusLogin(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        mView.startRequest(signInIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_GOOGLE_SIGN_IN) {
            checkGoogleSignInCallback(data);
        }
    }

    private void checkGoogleSignInCallback(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            mView.userLogin(Constants.LOGIN_STATUS_GOOGLE_PLUS);
        } else {
            mView.showError();
        }
    }

    @Override
    public GoogleApiClient.OnConnectionFailedListener getGoogleConnectionListener() {
        return mGoogleConnectionListener;
    }
}
