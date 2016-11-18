package com.oleg.hubal.authorizationapp.view.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.oleg.hubal.authorizationapp.Constants;
import com.oleg.hubal.authorizationapp.MainActivity;
import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.model.User;
import com.oleg.hubal.authorizationapp.presenter.profile.FacebookProfilePresenter;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenterContract;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 17.11.2016.
 */

public class ProfileFragment extends Fragment implements ProfileViewContract {

    private int PICK_IMAGE_REQUEST = 1;

    private ProfilePresenterContract mPresenter;
    private UserLogoutListener mLogoutListener;

    private CallbackManager mCallbackManager;

    private TextView tvUserName;
    private TextView tvUserEmail;
    private TextView tvUserBirthday;
    private ImageView ivUserImage;
    private EditText etShareMessage;
    private ImageView ivShareImage;

    private Uri mImageUri;

    View.OnClickListener mOpenGalleryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    };

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
        switch (getLoginStatus()) {
            case Constants.LOGIN_STATUS_FACEBOOK:
                mPresenter = new FacebookProfilePresenter(ProfileFragment.this);
                mCallbackManager = CallbackManager.Factory.create();
                break;
            default:
                userLogout();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvUserEmail = (TextView) view.findViewById(R.id.tv_user_email);
        tvUserBirthday = (TextView) view.findViewById(R.id.tv_user_birthday);
        ivUserImage = (ImageView) view.findViewById(R.id.iv_user_image);
        etShareMessage = (EditText) view.findViewById(R.id.et_share_text);
        ivShareImage = (ImageView) view.findViewById(R.id.iv_share_image);

        ivShareImage.setOnClickListener(mOpenGalleryClickListener);

        Button btnLogout = (Button) view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onUserLogout();
            }
        });

        initShare(view);

        mPresenter.onFillUserProfile();

        return view;
    }

    private void initShare(View view) {
        String caption = etShareMessage.getText().toString();
        Button btnShare = (Button) view.findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caption = etShareMessage.getText().toString();
                mPresenter.onShareData(caption, mImageUri);
            }
        });
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
        changeLoginStatus();
        mLogoutListener.showLoginFragment();
    }

    private void changeLoginStatus() {
        SharedPreferences sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt(Constants.PREF_LOGIN_STATUS, Constants.LOGIN_STATUS_NONE);
        editor.apply();
    }

    @Override
    public void showSuccessShare(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            showSharePhoto(data);
        }
    }

    private void showSharePhoto(Intent data) {
        mImageUri = data.getData();
        Picasso.with(getContext()).load(mImageUri).into(ivShareImage);
    }

    public int getLoginStatus() {
        SharedPreferences sPref = getActivity().getPreferences(MODE_PRIVATE);
        return sPref.getInt(Constants.PREF_LOGIN_STATUS, Constants.LOGIN_STATUS_NONE);
    }
}
