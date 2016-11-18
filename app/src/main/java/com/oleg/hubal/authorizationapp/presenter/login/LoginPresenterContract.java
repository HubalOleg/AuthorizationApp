package com.oleg.hubal.authorizationapp.presenter.login;

import android.content.Intent;

import com.oleg.hubal.authorizationapp.presenter.BasePresenter;

/**
 * Created by User on 16.11.2016.
 */

public interface LoginPresenterContract extends BasePresenter {

    void onFacebookLogin();
    void onActivityResult(int requestCode, int resultCode, Intent data);

}
