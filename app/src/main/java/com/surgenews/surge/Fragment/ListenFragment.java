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

import eu.fiskur.chipcloud.ChipCloud;
import eu.fiskur.chipcloud.ChipListener;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ListenFragment extends Fragment implements View.OnClickListener{

    public static final String ARG_OBJECT = "object";
    ImageView mPlayButton;
    MediaPlayer mMediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listen_fragment, container, false);
        ChipCloud chipCloud = (ChipCloud) rootView.findViewById(R.id.chip_cloud);
        mPlayButton = (ImageView) rootView.findViewById(R.id.iv_play_btn);
        mMediaPlayer = new MediaPlayer();
        mPlayButton.setOnClickListener(this);
        String[] a = {"Politics","Sports","Tech"};
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
        switch (id){
            case R.id.iv_play_btn:
                /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
                try {
                    mMediaPlayer.setDataSource("https://surgenews.s3.amazonaws.com/8333db20-05bf-4569-9533-8d1e9536c4c1.3gp"); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    mMediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!mMediaPlayer.isPlaying()){
                    mMediaPlayer.start();
                    mPlayButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }else {
                    mMediaPlayer.pause();
                    mPlayButton.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                }
                break;
        }
    }
}
