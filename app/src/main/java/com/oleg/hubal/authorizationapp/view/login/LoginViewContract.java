package com.oleg.hubal.authorizationapp.view.login;

import android.content.Intent;

/**
 * Created by User on 16.11.2016.
 */

public interface LoginViewContract {

    void showError();
    void userLogin(int loginStatus);
    void startRequest(Intent intent);

    interface UserLoginListener {
        void showProfileFragment();
    }
}
