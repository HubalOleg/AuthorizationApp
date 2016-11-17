package com.oleg.hubal.authorizationapp.view.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenter;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenterContract;
import com.oleg.hubal.authorizationapp.view.MainActivity;

/**
 * Created by User on 17.11.2016.
 */

public class ProfileFragment extends Fragment implements ProfileViewContract {

    private UserLogoutListener mLogoutListener;

    private ProfilePresenterContract mPresenter;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLogoutListener = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mPresenter = new ProfilePresenter(ProfileFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void userLogout() {
        mLogoutListener.showLoginFragment();
    }

    public interface UserLogoutListener {
        void showLoginFragment();
    }
}
