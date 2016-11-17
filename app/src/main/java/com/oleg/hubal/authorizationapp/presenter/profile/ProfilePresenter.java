package com.oleg.hubal.authorizationapp.presenter.profile;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.oleg.hubal.authorizationapp.view.profile.ProfileViewContract;

import org.json.JSONObject;

/**
 * Created by User on 17.11.2016.
 */

public class ProfilePresenter implements ProfilePresenterContract {

    private ProfileViewContract mView;

    GraphRequest.GraphJSONObjectCallback mJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            parseJSONData();
        }
    };

    public ProfilePresenter(ProfileViewContract view) {
        mView = view;
    }

    @Override
    public void onStop() {

    }

    private void parseJSONData() {

    }
}
