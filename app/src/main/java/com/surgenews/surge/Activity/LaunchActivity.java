package com.surgenews.surge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.surgenews.surge.Controller.WebserviceClient;
import com.surgenews.surge.Controller.WebserviceInterface;
import com.surgenews.surge.Fragment.LoginRegisterFragment;
import com.surgenews.surge.Model.SignUpRequest;
import com.surgenews.surge.Model.SignupResponse;
import com.surgenews.surge.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anishhegde on 10/09/16.
 */
public class LaunchActivity extends AppCompatActivity implements LoginRegisterFragment.Callback{

    public static final int LOGIN = 0;
    public static final int SIGNUP = 1;

    AppPagerAdapter mAppPagerAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAppPagerAdapter = new AppPagerAdapter(getSupportFragmentManager(),this);
        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.login_pager);
        mViewPager.setAdapter(mAppPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onLoginClicked() {
        openNews();
    }

    @Override
    public void onSignUpClicked() {
        //new SignUpTask().execute();
        signUp();
    }

    void openNews(){
        Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class AppPagerAdapter extends FragmentStatePagerAdapter {
        LoginRegisterFragment.Callback mCallback;

        public AppPagerAdapter(FragmentManager fm, LoginRegisterFragment.Callback callback) {
            super(fm);
            mCallback = callback;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            if(i == 0){
                fragment = new LoginRegisterFragment();
                Bundle args = new Bundle();
                args.putInt("TYPE",LOGIN);
                fragment.setArguments(args);
            } else {
                fragment = new LoginRegisterFragment();
                Bundle args = new Bundle();
                args.putInt("TYPE",SIGNUP);
                fragment.setArguments(args);
            }
            ((LoginRegisterFragment)fragment).setCallback(mCallback);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "Login";
            } else {
                return "SignUp";
            }
        }
    }
    void signUp(){
        WebserviceInterface webserviceInterface = WebserviceClient.getClient().create(WebserviceInterface.class);
        Call<SignupResponse> call = webserviceInterface.signUp(new SignUpRequest("anish","admin"));
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                Log.d("SignUp", "Sign up token: " + response.body().getToken());
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Log.e("SignUp", t.toString());
            }
        });

    }

    void login(){
        WebserviceInterface webserviceInterface = WebserviceClient.getClient().create(WebserviceInterface.class);
        Call<SignupResponse> call = webserviceInterface.signUp(new SignUpRequest("anish","admin"));
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                Log.d("SignIn", "Sign In token: " + response.body().getToken());
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Log.e("SignIn", t.toString());
            }
        });

    }
}
