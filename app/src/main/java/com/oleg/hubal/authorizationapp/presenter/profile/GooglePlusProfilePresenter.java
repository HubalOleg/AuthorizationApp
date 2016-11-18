package com.oleg.hubal.authorizationapp.presenter.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.oleg.hubal.authorizationapp.Constants;
import com.oleg.hubal.authorizationapp.model.User;
import com.oleg.hubal.authorizationapp.view.profile.ProfileViewContract;

/**
 * Created by User on 18.11.2016.
 */

public class GooglePlusProfilePresenter implements ProfilePresenterContract {

    private User mUser;
    private ProfileViewContract mView;
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;

    private ResultCallback<People.LoadPeopleResult> mLoadPeopleCallback = new ResultCallback<People.LoadPeopleResult>() {
        @Override
        public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
            Person person = loadPeopleResult.getPersonBuffer().get(0);

            mUser.setBirthday(person.getBirthday());
            mUser.setName(person.getDisplayName());

            mView.showUserData(mUser);
        }
    };

    public GooglePlusProfilePresenter(Context context, ProfileViewContract view) {
        mContext = context;
        mView = view;
        mUser = new User();
        initGoogleClient(context);
    }

    private void initGoogleClient(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();
        mGoogleApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
    }

    @Override
    public void onUserLogout() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mView.userLogout();
    }



    @Override
    public void onShareData(String caption, Uri photoUri) {
        PlusShare.Builder builder = new PlusShare.Builder(mContext);
        builder.addCallToAction("CREATE_ITEM", Uri.parse("http://plus.google.com/pages/create"), "/pages/create");
        builder.setContentUrl(Uri.parse("https://plus.google.com/pages/"));
        builder.setContentDeepLinkId("/pages/", null, null, null);

    }

    @Override
    public void onFillUserProfile() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mView.startRequest(signInIntent, Constants.REQUEST_GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        GoogleSignInAccount account = result.getSignInAccount();
        mUser.setImageURL(account.getPhotoUrl().toString());
        mUser.setEmail(account.getEmail());
        Plus.PeopleApi.load(mGoogleApiClient, "me").setResultCallback(mLoadPeopleCallback);
    }

    @Override
    public void onStop() {

    }
}
