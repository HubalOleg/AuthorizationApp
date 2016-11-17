package com.oleg.hubal.authorizationapp.view.profile;

import com.facebook.share.model.ShareContent;
import com.oleg.hubal.authorizationapp.model.User;

/**
 * Created by User on 17.11.2016.
 */

public interface ProfileViewContract {

    void showUserData(User user);
    void userLogout();
    void showShareDialog(ShareContent content);
    void showError(String error);

}
