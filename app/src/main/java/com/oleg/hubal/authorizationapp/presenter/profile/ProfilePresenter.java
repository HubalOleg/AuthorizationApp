package com.oleg.hubal.authorizationapp.presenter.profile;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
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

    private GraphRequest.GraphJSONObjectCallback mJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            parseJSONData(object);
        }
    };

    private FacebookCallback mFacebookShareCallback = new FacebookCallback() {
        @Override
        public void onSuccess(Object o) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            mView.showError("Share error");
            error.printStackTrace();
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
    public void shareData(String message, byte[] data) {
//        SharePhoto photo = new SharePhoto.Builder()
//                .setImageUrl(imageUrl)
//                .build();
//        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
//                .putString("og:type", "article")
//                .putString("og:description", message)
//                .putPhoto("og:image", photo)
//                .build();
//        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
//                .setActionType("article")
//                .putObject("article",object)
//                .build();
//        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
//                .setPreviewPropertyName("article")
//                .setAction(action)
//                .build();

//        GraphRequest graphRequest = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(),
//                "/me/objects/article", object, new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        Log.d(TAG, "onCompleted: " + );
//                    }
//                });


        Bundle params = new Bundle();
        params.putString("caption", message);

        params.putByteArray("picture", data);

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, "onCompleted: ");

                    }
                }
        ).executeAsync();

//        mView.showShareDialog(content);

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

    @Override
    public FacebookCallback getFacebookShareCallback() {
        return mFacebookShareCallback;
    }
}
