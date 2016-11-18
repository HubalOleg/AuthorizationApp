package com.oleg.hubal.authorizationapp.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.SignInButton;
import com.oleg.hubal.authorizationapp.Constants;
import com.oleg.hubal.authorizationapp.MainActivity;
import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.presenter.login.LoginPresenter;
import com.oleg.hubal.authorizationapp.presenter.login.LoginPresenterContract;

import java.util.Arrays;

/**
 * Created by User on 16.11.2016.
 */

public class LoginFragment extends Fragment implements LoginViewContract {

    private static final String PERMISSIONS = "public_profile, email, user_birthday";

    private LoginPresenterContract mPresenter;
    private UserLoginListener mLoginListener;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();

        return loginFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoginListener = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mPresenter = new LoginPresenter(LoginFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initFacebookLoginButton(view);
        initGooglePlusLoginButton(view);

        return view;
    }

    private void initFacebookLoginButton(View view) {
        Button btnFacebookLogin = (Button) view.findViewById(R.id.btn_facebook_login);
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList(PERMISSIONS));
                mPresenter.onFacebookLogin();
            }
        });
    }

    private void initGooglePlusLoginButton(View view) {
        SignInButton signInButton = (SignInButton) view.findViewById(R.id.btn_googleplus_login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onGooglePlusLogin(getContext());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userLogin(int loginStatus) {
        changeLoginStatus(loginStatus);
        mLoginListener.showProfileFragment();
    }

    @Override
    public void startRequest(Intent intent) {
        startActivityForResult(intent, Constants.REQUEST_GOOGLE_SIGN_IN);
    }

    private void changeLoginStatus(int loginStatus) {
        SharedPreferences sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt(Constants.PREF_LOGIN_STATUS, loginStatus);
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onStop();
    }
}
