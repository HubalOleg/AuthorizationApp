package com.oleg.hubal.authorizationapp.presenter.profile;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.oleg.hubal.authorizationapp.Utils;
import com.oleg.hubal.authorizationapp.model.User;
import com.oleg.hubal.authorizationapp.view.profile.ProfileViewContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 17.11.2016.
 */

public class ProfilePresenter implements ProfilePresenterContract {

    private static final String TAG = "ProfilePresenter";

    private static final String BUNDLE_KEY = "fields";
    private static final String PARAMETERS = "id,name,email,birthday";

    private ProfileViewContract mView;

    GraphRequest.GraphJSONObjectCallback mJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            parseJSONData(object);
        }
    };

    public ProfilePresenter(ProfileViewContract view) {
        mView = view;
    }

    @Override
    public void onStop() {
    }

    private void parseJSONData(JSONObject object) {
        try {
            String id = object.getString("id");
            String name = object.getString("name");
            String email = object.getString("email");
            String birthday = object.getString("birthday");
            String imageURL = "https://graph.facebook.com/" + id + "/picture?type=large";
            User user = new User(name, email, birthday, imageURL);
            sendData(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendData(User user) {
        mView.showUserData(user);
    }

    @Override
    public void userLogout() {
        if (Utils.isLoggedIn(AccessToken.getCurrentAccessToken())) {
            LoginManager.getInstance().logOut();
            mView.userLogout();
        }
    }

    @Override
    public void shareData() {

    }

    @Override
    public void fillUserProfile() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(), mJSONObjectCallback
        );
        Bundle parameters = new Bundle();
        parameters.putString(BUNDLE_KEY, PARAMETERS);
        request.setParameters(parameters);
        request.executeAsync();
    }
}
