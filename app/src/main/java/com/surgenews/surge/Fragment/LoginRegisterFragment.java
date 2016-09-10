package com.surgenews.surge.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.surgenews.surge.Activity.LaunchActivity;
import com.surgenews.surge.R;

/**
 * Created by anishhegde on 10/09/16.
 */
public class LoginRegisterFragment extends Fragment {

    Button mLoginButton;
    Callback mCallback;
    int mType;

    public void setCallback(Callback callback){
        mCallback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginButton = (Button) rootView.findViewById(R.id.btn_login);
        Bundle args = getArguments();
        mType = args.getInt("TYPE",0);
        if(mType == LaunchActivity.LOGIN) {
            mLoginButton.setText("Login");
        } else {
            mLoginButton.setText("SignUp");
        }
      setLoginButtonListener();
        return rootView;
    }

    void setLoginButtonListener(){
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mType == LaunchActivity.LOGIN){
                    if(mCallback != null){
                        mCallback.onLoginClicked();
                    }
                } else {
                    if(mCallback != null){
                        mCallback.onSignUpClicked();
                    }
                }
            }
        });
    }
    public interface Callback{
        void onLoginClicked();
        void onSignUpClicked();
    }
}


