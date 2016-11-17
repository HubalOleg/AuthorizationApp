package com.oleg.hubal.authorizationapp.view.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.model.User;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenter;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenterContract;
import com.oleg.hubal.authorizationapp.view.MainActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 17.11.2016.
 */

public class ProfileFragment extends Fragment implements ProfileViewContract {

    public static final String TAG = "asd";
    private ProfilePresenterContract mPresenter;
    private UserLogoutListener mLogoutListener;

    private TextView tvUserName;
    private TextView tvUserEmail;
    private TextView tvUserBirthday;
    private ImageView ivUserImage;

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

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvUserEmail = (TextView) view.findViewById(R.id.tv_user_email);
        tvUserBirthday = (TextView) view.findViewById(R.id.tv_user_birthday);
        ivUserImage = (ImageView) view.findViewById(R.id.iv_user_image);

        Button btnLogout = (Button) view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.userLogout();
            }
        });

        Button btnShare = (Button) view.findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                mPresenter.shareData();
            }
        });

        mPresenter.fillUserProfile();

        return view;
    }

    @Override
    public void showUserData(User user) {
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
        tvUserBirthday.setText(user.getBirthday());
        Picasso.with(getContext()).load(user.getImageURL()).into(ivUserImage);
    }

    @Override
    public void userLogout() {
        mLogoutListener.showLoginFragment();
    }

    public interface UserLogoutListener {
        void showLoginFragment();
    }
}
