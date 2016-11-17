package com.oleg.hubal.authorizationapp.presenter.profile;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.oleg.hubal.authorizationapp.view.profile.ProfileViewContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 17.11.2016.
 */

public class ProfilePresenter implements ProfilePresenterContract {

    private ProfileViewContract mView;

    public ProfilePresenter(ProfileViewContract view) {
        mView = view;
    }

    @Override
    public void onStop() {

    }

    @Override
    public void fillEmailTextView() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                String email = object.getString("email");
                                mView.showEmail(email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            );
            Bundle parameters = new Bundle();
            parameters.putString("fields", "email");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
}
