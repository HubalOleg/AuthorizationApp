package com.oleg.hubal.authorizationapp.presenter.login;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.oleg.hubal.authorizationapp.presenter.BasePresenter;

/**
 * Created by User on 16.11.2016.
 */

public interface LoginPresenterContract extends BasePresenter {

    FacebookCallback<LoginResult> getFacebookLoginCallback();

}
