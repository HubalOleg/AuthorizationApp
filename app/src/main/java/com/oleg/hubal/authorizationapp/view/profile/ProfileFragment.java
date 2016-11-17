package com.oleg.hubal.authorizationapp.view.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareDialog;
import com.oleg.hubal.authorizationapp.R;
import com.oleg.hubal.authorizationapp.model.User;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenter;
import com.oleg.hubal.authorizationapp.presenter.profile.ProfilePresenterContract;
import com.oleg.hubal.authorizationapp.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

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
        mPresenter = new ProfilePresenter(ProfileFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mCallbackManager = CallbackManager.Factory.create();

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
                mPresenter.userLogout();
            }
        });

        Button btnShare = (Button) view.findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final byte[] data = stream.toByteArray();
                    mPresenter.shareData(etShareMessage.getText().toString(), data);
                } catch(IOException e) {

                }

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

    @Override
    public void showShareDialog(ShareContent content) {
        ShareDialog share = new ShareDialog(ProfileFragment.this);
        share.registerCallback(mCallbackManager ,mPresenter.getFacebookShareCallback());
        share.show(content);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            setImageFromGallery(data.getData());
        }
    }

    private void setImageFromGallery(Uri uri) {
        mImageUri = uri;
        Picasso.with(getContext()).load(uri).into(ivShareImage);
    }

    public interface UserLogoutListener {
        void showLoginFragment();
    }
}
