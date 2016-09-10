package com.surgenews.surge.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.surgenews.surge.Fragment.ListenFragment;
import com.surgenews.surge.Fragment.LoginRegisterFragment;
import com.surgenews.surge.R;
import com.surgenews.surge.Fragment.ReadFragment;

/**
 * Created by anishhegde on 10/09/16.
 */
public class LaunchActivity extends AppCompatActivity {

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

        mAppPagerAdapter = new AppPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.login_pager);
        mViewPager.setAdapter(mAppPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class AppPagerAdapter extends FragmentStatePagerAdapter {

        ReadFragment.Callback mCallback;
        public AppPagerAdapter(FragmentManager fm) {
            super(fm);
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
}