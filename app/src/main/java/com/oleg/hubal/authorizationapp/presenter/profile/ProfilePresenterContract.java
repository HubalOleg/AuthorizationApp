package com.oleg.hubal.authorizationapp.presenter.profile;

import android.content.Intent;
import android.net.Uri;

import com.oleg.hubal.authorizationapp.presenter.BasePresenter;

/**
 * Created by User on 17.11.2016.
 */

public interface ProfilePresenterContract extends BasePresenter {

    void onUserLogout();
    void onShareData(String caption, Uri photoUri);
    void onFillUserProfile();
    void onActivityResult(int requestCode, int resultCode, Intent data);

}
