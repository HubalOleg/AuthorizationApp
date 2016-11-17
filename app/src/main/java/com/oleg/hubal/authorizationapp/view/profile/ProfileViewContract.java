package com.oleg.hubal.authorizationapp.view.profile;

import com.oleg.hubal.authorizationapp.model.User;

/**
 * Created by User on 17.11.2016.
 */

public interface ProfileViewContract {

    void showUserData(User user);
    void userLogout();

}
