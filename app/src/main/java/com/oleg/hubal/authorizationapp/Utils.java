package com.oleg.hubal.authorizationapp;

import com.facebook.AccessToken;

/**
 * Created by User on 17.11.2016.
 */

public class Utils {

    public static boolean isLoggedIn(AccessToken accessToken) {
        if (accessToken == null)
            return false;
        else
            return true;
    }
}
