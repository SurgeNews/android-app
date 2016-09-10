package com.surgenews.surge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ReadFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<RssModelItem> mDataSet;
    private Callback mCallback;

    public void setCallback(Callback callback){
        mCallback = callback;
    }

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
        mDataSet = new ArrayList<>();
        mAdapter = new NewsFeedAdapter(mDataSet,getActivity().getApplicationContext(),mCallback);
        mRecyclerView.setAdapter(mAdapter);
        setFeed();

        return rootView;
    }

    void setFeed(){
        DownloadManager.Instance().downloadData("http://rss.nytimes.com/services/xml/rss/nyt/InternationalHome.xml", new DownloadManager.Callback() {
            @Override
            public void onFeed(Feed feed) {
                for (Item item : feed.getItems()) {
                    RssModelItem rssItem = new RssModelItem();
                    rssItem.setDescription(item.getDescription());
                    rssItem.setTitle(item.getTitle());
                    rssItem.setImageUrl(item.getImageLink());
                    rssItem.setFeedLink(item.getLink());
                    mDataSet.add(rssItem);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    public interface Callback{
        void onArticleClick(String url);
    }
}
