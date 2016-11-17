package com.oleg.hubal.authorizationapp.presenter.profile;

import com.facebook.FacebookCallback;
import com.oleg.hubal.authorizationapp.presenter.BasePresenter;

/**
 * Created by User on 17.11.2016.
 */

public interface ProfilePresenterContract extends BasePresenter {

    void userLogout();
    void shareData(String message, byte[] data);
    void fillUserProfile();
    FacebookCallback getFacebookShareCallback();

}
