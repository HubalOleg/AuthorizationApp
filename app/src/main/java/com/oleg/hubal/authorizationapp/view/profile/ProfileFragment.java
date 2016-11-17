package com.oleg.hubal.authorizationapp.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenter;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenterContract;

/**
 * Created by User on 17.11.2016.
 */

public class ProfileFragment extends Fragment implements ProfileViewContract {

    private static final String TAG = "ProfileFragment";

    private ProfilePresenterContract mPresenter;

    private TextView mEmailTextView;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
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
        Log.d(TAG, "onCreateView: ");
        mEmailTextView = (TextView) view.findViewById(R.id.tv_email);

        mPresenter.fillEmailTextView();

        Button btnLogout = (Button) view.findViewById(R.id.logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                getActivity().recreate();
            }
        });

        return view;
    }

    @Override
    public void showEmail(String email) {
        mEmailTextView.setText(email);
    }
}
