package com.surgenews.surge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ReadFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataSet = {"Hey check this out","What are you doing","You are missing your party"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.read_fragment, container, false);
        Bundle args = getArguments();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_news);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NewsFeedAdapter(mDataSet);
        mRecyclerView.setAdapter(mAdapter);
        setFeed();

        return rootView;
    }

    void setFeed(){
        DownloadManager.Instance().downloadData("https://news.google.com/news?q=soccer&output=rss", new DownloadManager.Callback() {
            @Override
            public void onFeed(Feed feed) {
                for (Item item : feed.getItems()) {
                    String title = item.getTitle();
                    Log.i("TAG", "Item title: " + (title == null ? "N/A" : title));
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
