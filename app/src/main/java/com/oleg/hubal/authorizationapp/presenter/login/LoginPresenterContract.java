package com.oleg.hubal.authorizationapp.presenter.login;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.oleg.hubal.authorizationapp.presenter.BasePresenter;

/**
 * Created by User on 16.11.2016.
 */

public interface LoginPresenterContract extends BasePresenter {

    void onFacebookLogin();
    void onGooglePlusLogin(Context context);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    GoogleApiClient.OnConnectionFailedListener getGoogleConnectionListener();
}
