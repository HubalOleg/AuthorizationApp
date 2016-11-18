package com.oleg.hubal.authorizationapp.view.profile;

import android.content.Intent;

import com.oleg.hubal.authorizationapp.model.User;

/**
 * Created by User on 17.11.2016.
 */

public interface ProfileViewContract {

    void showUserData(User user);
    void userLogout();
    void showSuccessShare(String message);
    void showError(String error);
    void startRequest(Intent intent, int requestCode);

    interface UserLogoutListener {
        void showLoginFragment();
    }

}
