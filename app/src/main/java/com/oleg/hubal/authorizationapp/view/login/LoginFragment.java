package com.oleg.hubal.authorizationapp.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.presenter.login.LoginPresenter;
import com.oleg.hubal.authorizationapp.presenter.login.LoginPresenterContract;
import com.oleg.hubal.authorizationapp.view.MainActivity;

import java.util.Arrays;

/**
 * Created by User on 16.11.2016.
 */

public class LoginFragment extends Fragment implements LoginViewContract {

    private static final String PERMISSIONS = "public_profile, email, user_birthday";

    private LoginPresenterContract mPresenter;
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
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
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = (LoginButton) view.findViewById(R.id.login_button);
        mLoginButton.setReadPermissions(Arrays.asList(PERMISSIONS));
        mLoginButton.setFragment(LoginFragment.this);

        mLoginButton.registerCallback(mCallbackManager, mPresenter.getFacebookLoginCallback());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userLogin() {
        mLoginListener.showProfileFragment();
    }

    public interface UserLoginListener {
        void showProfileFragment();
    }


}
