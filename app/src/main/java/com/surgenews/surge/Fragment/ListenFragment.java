package com.surgenews.surge.Fragment;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.surgenews.surge.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import eu.fiskur.chipcloud.ChipCloud;
import eu.fiskur.chipcloud.ChipListener;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ListenFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_OBJECT = "object";
    ImageView mPlayButton;
    MediaPlayer mMediaPlayer;
    ArrayList<String> mAudioFeedList;
    String mPresentAUDIO ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listen_fragment, container, false);
        ChipCloud chipCloud = (ChipCloud) rootView.findViewById(R.id.chip_cloud);
        mPlayButton = (ImageView) rootView.findViewById(R.id.iv_play_btn);
        ((ImageView) rootView.findViewById(R.id.iv_forward)).setOnClickListener(this);
        ((ImageView) rootView.findViewById(R.id.iv_forward)).setOnClickListener(this);
        mPlayButton.setOnClickListener(this);
        mMediaPlayer = new MediaPlayer();
        mAudioFeedList = new ArrayList<>();
        mAudioFeedList.add("https://surgenews.s3.amazonaws.com/3e6a65b3-e3e5-4b0d-9452-298b1e7c2a97.3gp");
        mAudioFeedList.add("https://surgenews.s3.amazonaws.com/610fe418-9bee-4eef-a21b-2ece33b74688.3gp");
        mAudioFeedList.add("https://surgenews.s3.amazonaws.com/7acd8996-3261-4f87-83fa-93abed202e28.3gp");
        String[] a = {"Politics", "Sports", "Tech"};
        mPresentAUDIO = mAudioFeedList.get(0);
        new ChipCloud.Configure()
                .chipCloud(chipCloud)
                .selectedColor(Color.parseColor("#ff00cc"))
                .selectedFontColor(Color.parseColor("#ffffff"))
                .deselectedColor(Color.parseColor("#ff00cc"))
                .deselectedFontColor(Color.parseColor("#ffffff"))
                .selectTransitionMS(0)
                .deselectTransitionMS(0)
                .labels(a)
                .mode(ChipCloud.Mode.MULTI)
                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                        //...
                    }

                    @Override
                    public void chipDeselected(int index) {
                        //...
                    }
                })
                .build();


        Bundle args = getArguments();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_play_btn:
                play();
                break;
            case R.id.iv_forward:
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                    mMediaPlayer = null;
                }
                mMediaPlayer = new MediaPlayer();
                playNext(true);
                break;
            case R.id.iv_rewind:
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                    mMediaPlayer = null;
                }
                mMediaPlayer = new MediaPlayer();
                playNext(true);
                break;

        }
    }

    void play() {
        /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
        try {
            mMediaPlayer.setDataSource(mPresentAUDIO); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
            mMediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            mPlayButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        } else {
            mMediaPlayer.pause();
            mPlayButton.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        }
    }

    void playNext(boolean shouldChange) {
        /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
        try {
            if (shouldChange) {
                mPresentAUDIO = mAudioFeedList.get(new Random().nextInt(2) + 1);
                mMediaPlayer.setDataSource(mPresentAUDIO); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                mMediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            mPlayButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        } else {
            mMediaPlayer.pause();
            mPlayButton.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        }
    }
}
