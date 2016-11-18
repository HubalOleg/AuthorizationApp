package com.oleg.hubal.authorizationapp.presenter.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.oleg.hubal.authorizationapp.Utils;
import com.oleg.hubal.authorizationapp.model.User;
import com.oleg.hubal.authorizationapp.view.profile.ProfileViewContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 17.11.2016.
 */

public class FacebookProfilePresenter implements ProfilePresenterContract {

    private static final String BUNDLE_KEY = "fields";
    private static final String PARAMETERS = "id,name,email,birthday";

    private ProfileViewContract mView;

    private GraphRequest.GraphJSONObjectCallback mJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            parseJSONData(object);
        }
    };

    private FacebookCallback mShareCallback = new FacebookCallback() {
        @Override
        public void onSuccess(Object o) {
            mView.showSuccessShare("Success Share");
        }

        @Override
        public void onCancel() {}

        @Override
        public void onError(FacebookException error) {
            mView.showError("Share Error");
        }
    };

    public FacebookProfilePresenter(ProfileViewContract view) {
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
    public void onUserLogout() {
        if (Utils.isLoggedIn(AccessToken.getCurrentAccessToken())) {
            LoginManager.getInstance().logOut();
            mView.userLogout();
        }
    }

    @Override
    public void onShareData(String caption, Uri photoUri) {
        SharePhoto photo = new SharePhoto
                .Builder()
                .setImageUrl(photoUri)
                .setCaption(caption)
                .build();

        SharePhotoContent content = new SharePhotoContent
                .Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, mShareCallback);
    }

    @Override
    public void onFillUserProfile() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(), mJSONObjectCallback
        );
        Bundle parameters = new Bundle();
        parameters.putString(BUNDLE_KEY, PARAMETERS);
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
